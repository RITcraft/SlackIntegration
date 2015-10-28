/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.RTMClient;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.UserList;
import edu.rit.chrisbitler.ritcraft.slackintegration.util.Util;
import org.bukkit.Bukkit;
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
            //Send users the message and username based on the mappings if
            //the username is not null (aka the bot), and the chhanel is the relay channel
            if(channel.equals(SlackIntegration.RELAY_CHANNEL)) {
                Util.broadcast(UserList.getName(userName) + ": " + text);
            }else if(channel.equals(SlackIntegration.ADMIN_CHANNEL)) {
                //Is message from admin channel, check if it starts with .command
                if(text.toLowerCase().startsWith(".command")) {
                    //It is a command, take everything after .command and treat it as a command.
                    String[] parts = text.split(" ");
                    String command = Util.combineArray(parts,1);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
                    SlackIntegration.getRTM().sendSlackMessage("Command sent!",channel);
                }
            }
        }
    }
}
