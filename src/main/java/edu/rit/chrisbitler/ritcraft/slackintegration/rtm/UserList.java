/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class to help maintain a list of user ids to usernames since
 * slack returns user ids in chat messages instead of real names
 */
public class UserList {
    private static HashMap<String,String> users = new HashMap<>(); //User id -> username map

    /**
     * Get a user's name based on their slack user id
     * @param id The user's slack id
     * @return Their 'real name' as per slack
     */
    public static String getName(String id) {
        if(users.get(id) != null) {
            return users.get(id);
        }else{
            queryUsers();
            return users.get(id);
        }
    }

    /**
     * Query the slack api to get the list of users and their real names. This is done whenever we can't find an ID mapping,
     * and when the plugin loads
     */
    public static void queryUsers() {
        try {
            users.clear();
            //Contact the slack api to get the user list
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.slack.com/api/users.list?token=" + SlackIntegration.BOT_TOKEN).openConnection();
            conn.connect();
            JSONObject retVal = (JSONObject) JSONValue.parse(new InputStreamReader((InputStream) conn.getContent()));
            JSONArray members = (JSONArray) retVal.get("members");

            //Loop through the members and add them to the map
            Iterator<JSONObject> iter =  members.iterator();
            while(iter.hasNext()) {
                JSONObject obj = iter.next();
                JSONObject profile = (JSONObject) obj.get("profile");
                String id = (String) obj.get("id");
                String realName = (String) profile.get("real_name");
                users.put(id,realName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
