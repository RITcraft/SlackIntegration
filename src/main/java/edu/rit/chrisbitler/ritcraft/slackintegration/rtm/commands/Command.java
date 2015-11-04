/*
 * Copyright 2015 RITcraft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
