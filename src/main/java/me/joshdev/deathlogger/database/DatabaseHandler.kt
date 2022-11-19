// Package name.
package me.joshdev.deathlogger.database

// Imports.
import me.joshdev.deathlogger.DeathLogger
import org.bukkit.entity.Player
import java.math.RoundingMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.text.DecimalFormat

class DatabaseHandler { // Class.

    private var plugin = DeathLogger.instance // Get plugin instance from main class.
    private val logger = DeathLogger.pluginLogger // Get the logger.
    private var dataFolder = plugin?.dataFolder?.absolutePath // Get absolute path of plugin data folder.
    private var connection: Connection = DriverManager.getConnection("jdbc:sqlite:".plus(dataFolder).plus("/data.db")) // Get connection to database.
    private var statement: Statement = connection.createStatement() // Create statement for executing sql commands.
    private val decimalFormatter = DecimalFormat("#.###") // Create the decimal formatter, which will format a double to 3 decimal places.

    init{ // Init function, which will execute before anything else.

        decimalFormatter.roundingMode = RoundingMode.CEILING // Set the rounding mode of the decimal formatter to ceiling.

    } // Closing of init function.

    fun createNewTable(player : Player){ // This function creates a new table with the players UUID.

        try{ // Enter a try.

            val iD = player.uniqueId.toString().replace("-", "") // Get UUID of player and remove dashes.
            val command = "CREATE TABLE IF NOT EXISTS ".plus(iD).plus(" (id INTEGER PRIMARY KEY AUTOINCREMENT, deathCoords TEXT, worldName TEXT);") // Make a sql command to create a table for the user.
            statement.execute(command) // Execute the sql command through the statement.

        }catch(e: Exception){ // If error, catch as exception and do something.

            logger?.info("Error while handling table create.") // Print error indication to console.
            logger?.info("Message: ".plus(e.message)) // Print error message to console.

        } // End of try-catch.

    } // End of function.

    fun insertData(player: Player){ // This function inserts the players death location into their table.

        try{ // Enter a try.

            val iD = player.uniqueId.toString().replace("-", "") // Get UUID of player and remove dashes.
            val playerLocation = player.location // Get location of player.
            val worldName = player.world.name // Get the players world name.
            val location = playerLocation.x.toString() + ", " + playerLocation.y.toString() + ", " + playerLocation.z.toString() // Format location into a readable string.
            val command = "INSERT INTO ".plus(iD).plus("(deathCoords, worldName) VALUES('").plus(location).plus("', '").plus(worldName).plus("');") // Create the insert command with the required data.
            statement.execute(command) // Execute the sql command through the statement.

        }catch(e : Exception){ // If error, catch exception and do something.

            logger?.info("Error when inserting data.") // Print error indication to console.
            logger?.info("Message: ".plus(e.message)) // Print error message to console.

        } // End of try-catch.

    } // End of function.

    fun retrieveData(player: Player){ // This function retrieves the players death from their table.

        try{ // Enter a try.

            val iD = player.uniqueId.toString().replace("-", "") // Get the players uuid and remove the dashes from it.
            val sql = "SELECT * FROM ".plus(iD).plus(" LIMIT 5;") // Create SQL Query with the players uuid.
            val result = statement.executeQuery(sql) // Execute the query and store the result.

            player.sendMessage("Your Death Locations:") // Send a message indicating the start of the list to the player.

            while(result.next()){ // For each row returned from the query.

                val locationID = result.getInt(1).toString() // Get the location id and convert to string.
                val coords = result.getString(2).split(", ") // Get the coordinates for the location and split with , delimiter.
                val worldName = result.getString(3) // Get the world name.
                var formattedCoords = "" // Create empty string for formatted coords.

                for(coord in coords){ // For each coord in coords.

                    val roundedCoord = decimalFormatter.format(coord.toDouble()).toString() // Convert coord to double, round it to 3 decimal places, then convert back to string again.
                    formattedCoords = formattedCoords.plus(roundedCoord).plus(", ") // Append formattedCoords with roundedCoord.

                } // Closing of for loop.

                formattedCoords = formattedCoords.substring(0, formattedCoords.length - 2) // Substring the last comma from the coordinates.
                player.sendMessage(locationID.plus(" | ").plus(formattedCoords).plus(" | ").plus(worldName)) // Send the coordinate and world name as a message to the player.

            } // Closing of while loop.

        }catch(e : Exception){ // If error, catch exception and do something.

            logger?.info("An error occurred with retrieving data.") // Print error indication to console.
            logger?.info("Message: ".plus(e.message)) // Print error message to console.

        } // End of try-catch.

    } // End of function.

} // End of class.