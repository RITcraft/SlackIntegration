/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands;

import java.util.HashMap;

/**
 * Central registry class for commands to be used with the bot
 */
public class CommandRegistry {
    public static HashMap<String,Command> commands = new HashMap<String,Command>(); //List of commands linking to their respective command class

    /**
     * Registers a command in the system - This method does not normally need to be accessed
     * creating a new instance of any kind of command will register it automatically
     * @param command The command text that represents the command without the
     *                exclamation point, ie 'players'
     * @param cmd The actual command class that processes the command
     */
    public static void registerCommand(String command, Command cmd) {
        commands.put(command.toLowerCase(),cmd);
        System.out.println("[Slack] Registered command " + command + "...");
    }

    /**
     * Checks to see if the command exists and returns it if it does
     * @param command The string of the command to check
     * @return The command that the string represents
     */
    public static Command getCommand(String command) {
        return commands.get(command.toLowerCase());
    }

}
