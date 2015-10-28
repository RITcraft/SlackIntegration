/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Utility class
 */
public class Util {

    /**
     * Utility method for getting the plugin message prefix
     * @return the plugin message prefix
     */
    public static String getPrefix() {
        return ChatColor.AQUA + "[" + ChatColor.GOLD + "Slack" + ChatColor.AQUA + "] " + ChatColor.WHITE;
    }

    /**
     * Utility method to broadcast to players with the slack prefix.
     * @param msg The message to broadcast
     */
    public static void broadcast(String msg) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(getPrefix() + msg);
        }
    }
}
