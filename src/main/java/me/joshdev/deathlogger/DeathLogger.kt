// Package Name.
package me.joshdev.deathlogger

// Imports.
import me.joshdev.deathlogger.events.OnPlayerDeath
import org.bukkit.plugin.java.JavaPlugin

// Main Class.
class DeathLogger : JavaPlugin() {

    override fun onEnable() {

        var pluginManager = server.pluginManager
        pluginManager.registerEvents(OnPlayerDeath(), this)
        logger.info("Death Logger has been enabled, enjoy!")

    }

    override fun onDisable() {

        logger.info("Death Logger has been disabled, goodbye!")

    }

}