/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types;

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
        if(userName != null && !userName.equals("null")) {
            //Send users the message and username based on the mappings if
            //the username is not null (aka the bot)
            Util.broadcast(UserList.getName(userName) + ": " + text);
        }
    }
}
