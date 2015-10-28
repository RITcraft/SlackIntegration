/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Command to show the number and list of players in a slack channel
 */
public class PlayersCommand extends Command {

    /**
     * Register the players command in all channels
     */
    public PlayersCommand() {
        super("players","all");
    }

    /**
     * Process the players command to show the number and list of all players
     * @param channel The channel the command was used in
     * @param args The arguments of the command (not used here)
     */
    @Override
    public void processCommand(String channel, String[] args) {
        String players = "Players ("+ Bukkit.getOnlinePlayers().size() +"): [";
        boolean first = true;
        for(Player p :Bukkit.getOnlinePlayers()) {
            if(first) {
                players += (ChatColor.stripColor(p.getDisplayName()));
            }else{
                players += (", " + ChatColor.stripColor(p.getDisplayName()));
            }
        }
        players+="]";
        SlackIntegration.getRTM().sendSlackMessage(players,channel);
    }
}
