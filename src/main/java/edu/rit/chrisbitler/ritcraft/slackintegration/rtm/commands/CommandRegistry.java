/*
 * Copyright 2015 RITcraft & Chris Bitler
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
