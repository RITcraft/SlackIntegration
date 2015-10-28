/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to represent a command that can be used in the bot chats
 */
public abstract class Command {
    List<String> channels = new ArrayList<String>(); //Channels that the command can be used in

    /**
     * Create a command using the specific name that works in the specified channels
     * @param command
     * @param channels
     */
    public Command(String command, String... channels) {
        CommandRegistry.registerCommand(command, this);
        for(int i = 0; i < channels.length; i++) {
            this.channels.add(channels[i]);
        }
    }

    /**
     * Get the channels this command would work in ('all' means every channel)
     * @return The channel(s) this command works in
     */
    public List<String> getApplicableChannels() {
        return channels;
    }

    /**
     * Abstract method to process the commands, meant to be implement in subclass
     * @param channel The channel the command was used in
     * @param args The arguments of the command
     */
    public abstract void processCommand(String channel, String[] args);
}
