// Package Name.
package me.joshdev.deathlogger

// Imports.
import me.joshdev.deathlogger.commands.ShowDeaths
import me.joshdev.deathlogger.events.OnPlayerDeath
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

// Main Class.
class DeathLogger : JavaPlugin() { // Main class inherits the JavaPlugin class.

    companion object{ // Companion to make the plugin instance, as well as the logger, available across the project.

        var instance: JavaPlugin? = null
        private set

        var pluginLogger: Logger? = null
        private set

    } // Closing of companion.

    override fun onEnable() { // onEnable override, which will run when the plugin is enabled.

        instance = this // Set instance to this class.
        pluginLogger = logger // Set plugin logger to logger.

        if(!this.dataFolder.exists()){ // If the data folder for the plugin does not exist.

            logger.info("No data folder exists (Most likely due to first run), created one.") // Send no data folder message to console.
            this.dataFolder.mkdir() // Make the data folder directory.

        } // Closing of if statement.

        val pluginManager = server.pluginManager // Get the servers plugin manager.
        pluginManager.registerEvents(OnPlayerDeath(), this) // Register the death event.
        this.getCommand("showdeaths")?.setExecutor(ShowDeaths()) // Register the show deaths command.
        logger.info("Death Logger has been enabled, enjoy!") // Send a message to the server indicating that the plugin is ready for use.

    } // Closing of function.

    override fun onDisable() { // onDisable override, which will run when the plugin is disabled.

        logger.info("Death Logger has been disabled, goodbye!") // Send a message to the server indicating that the plugin was disabled.

    } // Closing of function.

} // Closing of class.