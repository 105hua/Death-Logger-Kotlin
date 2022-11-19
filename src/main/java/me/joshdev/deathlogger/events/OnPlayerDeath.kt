// Package Name
package me.joshdev.deathlogger.events

// Imports.
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import me.joshdev.deathlogger.database.DatabaseHandler

class OnPlayerDeath : Listener { // The OnPlayerDeath class inherits the Listener class.

    private val dbManager = DatabaseHandler() // Get the database handler.

    @EventHandler // Indicate that the following function is an event handler.
    fun onPlayerDeath(e : EntityDeathEvent){ // Create a function that handles the player's death, taking an EntityDeathEvent var as input.

        if(e.entity is Player){ // If the entity passed is a player.

            val player = e.entity as Player // Create a player var that stores the entity as a player.
            dbManager.createNewTable(player) // Create a new table for the player if it does not exist.
            dbManager.insertData(player) // Insert the data for the player.

        } // Closing of if statement.

    } // Closing of event function

} // Closing of event class.