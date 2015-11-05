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
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.UserList;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands.Command;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands.CommandRegistry;
import edu.rit.chrisbitler.ritcraft.slackintegration.util.Util;
import org.json.simple.JSONObject;

/**
 * Small class for handling 'message' type events from the slack api
 */
public class MsgMessage {

    /**
     * Handle the 'message' type event from slack
     * @param object The event object passed from slack
     */
    public static void handle(JSONObject object) {
        String userName = (String) object.get("user");
        String text = (String) object.get("text");
        String channel = (String) object.get("channel");
        if(userName != null && !userName.equals("null")) {
            //If the text doesn't start with a !, which denotes a command,
            //Send users the message and username based on the mappings if
            //the username is not null (aka the bot), and the chhanel is the relay channel
            if(!text.startsWith("!")) {
                if (channel.equals(SlackIntegration.RELAY_CHANNEL)) {
                    Util.broadcast(UserList.getName(userName) + ": " + text);
                }
            }else{
                //Split the message into parts based on spaces and check the command to see
                //if it exists
                String[] parts = text.split(" ");
                String command = parts[0].substring(1);
                if(CommandRegistry.getCommand(command) != null) {
                    Command cmd = CommandRegistry.getCommand(command);
                    //Check to see if we are in the right channel for the command
                    if(cmd.getApplicableChannels().contains(channel) || (cmd.getApplicableChannels().size() == 1 && cmd.getApplicableChannels().get(0).equalsIgnoreCase("all"))) {
                        //Process it with the args shifted forward by one
                        cmd.processCommand(channel,Util.shiftArray(parts,1));
                    }
                }
            }
        }
    }
}
