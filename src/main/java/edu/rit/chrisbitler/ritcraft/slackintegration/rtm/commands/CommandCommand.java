/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import edu.rit.chrisbitler.ritcraft.slackintegration.util.Util;
import org.bukkit.Bukkit;

/**
 * Admin channel command to issue server commands
 */
public class CommandCommand extends Command {

    /**
     * Creates the command to be used in only admin channels
     */
    public CommandCommand() {
        super("command", SlackIntegration.ADMIN_CHANNEL);
    }

    /**
     * The command takes all of the arguments and combines them into one thing, then sends it to
     * the server to process as a console command
     * @param channel The channel the command was sent from (not used)
     * @param args The arguments of the command
     */
    @Override
    public void processCommand(String channel, String[] args) {
        String command = Util.combineArray(args,0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
        SlackIntegration.getRTM().sendSlackMessage("Command sent!",SlackIntegration.ADMIN_CHANNEL);
    }
}
