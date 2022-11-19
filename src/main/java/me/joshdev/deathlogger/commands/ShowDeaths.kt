// Package name.
package me.joshdev.deathlogger.commands

// Imports.
import me.joshdev.deathlogger.database.DatabaseHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShowDeaths : CommandExecutor{ // The show deaths class inherits the command executor.

    private val dbManager = DatabaseHandler()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean { // This function is ran when the show deaths command is ran.

        if(sender is Player){ // If the sender is a player.

            val player: Player = sender // Get the sender as a player.
            dbManager.retrieveData(player) // Retrieve data through handler.
            return true // Return true.

        } // Closing of if statement.

        return false // Return false, indicating that the command has not been run correctly.

    } // Closing of function.

} // Closing of class.