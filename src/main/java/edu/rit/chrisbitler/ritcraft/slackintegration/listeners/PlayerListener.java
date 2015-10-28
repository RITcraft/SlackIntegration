/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.listeners;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Class for listening to player events from the spigot server
 */
public class PlayerListener implements Listener {
    SlackIntegration plugin; //The plugin itself, used for getting access to the real-time messaging client

    /**
     * Create a new listener for player-based events
     * @param plugin The plugin this listener is being added to
     */
    public PlayerListener(SlackIntegration plugin) {
        this.plugin = plugin;
    }

    /**
     * Event called by spigot when a player chats
     * This sends the message over slack to show that a player chatted ingame so that the bot says
     * they chatted in the channel
     * @param e The player chat event.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        plugin.getRTM().sendSlackMessage("[" + e.getPlayer().getName() + "]: " + ChatColor.stripColor(e.getMessage()),SlackIntegration.RELAY_CHANNEL);
    }

}
