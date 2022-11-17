package me.joshdev.deathlogger.events

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import java.sql.Connection
import java.sql.DriverManager

class OnPlayerDeath : Listener {

    var workingDir = System.getProperty("user.dir")

    @EventHandler
    fun onPlayerDeath(e : EntityDeathEvent){

        if(e.entity is Player){

            val player = e.entity as Player
            val playerID = player.uniqueId.toString().replace("-", "")

            try{

                var url = "jdbc:sqlite:".plus(workingDir).plus("/test.db")
                var conn = DriverManager.getConnection(url)

                // Make sure table exists first.
                var existsCommand =
                    """
                        CREATE TABLE IF NOT EXISTS $playerID(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        deathCoords TEXT
                        );
                    """.trimIndent()
                var existsStatement = conn.createStatement()
                existsStatement.execute(existsCommand)

                // Insert data into table.
                var deathLocation = e.entity.location
                var locationString = deathLocation.x.toString().plus(", ").plus(deathLocation.y.toString()).plus(", ").plus(deathLocation.z.toString())

                println(player.name.plus(" died at ").plus(locationString))

                var insertCommand =
                    """
                        INSERT INTO $playerID(deathCoords) VALUES(
                        '$locationString'
                        );
                    """.trimIndent()
                var insertStatement = conn.createStatement()
                insertStatement.execute(insertCommand)

            }catch(e : Exception){

                println("An error was produced.")
                println(e.message)

            }

        }

    }

}