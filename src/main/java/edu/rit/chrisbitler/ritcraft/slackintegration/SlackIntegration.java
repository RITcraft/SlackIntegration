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
package edu.rit.chrisbitler.ritcraft.slackintegration;

import com.ammaraskar.adminonly.AdminChat;
import edu.rit.chrisbitler.ritcraft.slackintegration.listeners.PlayerListener;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.RTMClient;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.UserList;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands.CommandCommand;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.commands.PlayersCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.tyrus.client.ClientManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Main plugin file for the SlackIntegration plugin
 */
public class SlackIntegration extends JavaPlugin {

    public static SlackIntegration instance; //Current instance of the plugin

    private static RTMClient rtm; //Main websocket client for communicating with slack

    private ClientManager client; //Websocket client from Tyrus implementation

    public static String BOT_TOKEN = ""; //Bot's api token, loaded from config
    public static  String RELAY_CHANNEL = ""; //Relay channel for displaying messages and reading chat
    public static String ADMIN_CHANNEL = "";

    public AdminChat adminChat;

    /**
     * Enable the plugin and do intial configuration
     */
    public void onEnable() {
        instance = this;
        System.out.println("Enabling Slack Integration");

        //Load the configuration data
        if(getConfig().getString("token") == null) {
            getConfig().addDefault("token","token");
            getConfig().addDefault("relay_channel","channel id");
            getConfig().addDefault("admin_channel","admin channel id");
            getConfig().options().copyDefaults(true);
            saveConfig();
            System.out.println("Please set the bot api token, relay channel id, and (optionally) the admin channel id in the configuration file before running.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }else{
            BOT_TOKEN =  getConfig().getString("token");
            RELAY_CHANNEL = getConfig().getString("relay_channel");
            ADMIN_CHANNEL = getConfig().getString("admin_channel");
        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        //Register the commands to be used with the bot
        new CommandCommand();
        new PlayersCommand();

        //Load adminprivatechat if it is there
        if(Bukkit.getPluginManager().getPlugin("AdminPrivateChat") != null) {
            adminChat = (AdminChat) Bukkit.getPluginManager().getPlugin("AdminPrivateChat");
        }

        try {
            //Start the real-time messaging with slack by querying their api with the token to get a websocket url
            startRTM();
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
                         if(rtm.dc == true) {
                             try {
                                 startRTM();
                             } catch (Exception e) {
                                 System.out.println("Slack is down, exiting plugin...");
                                 Bukkit.getPluginManager().disablePlugin(this);
                             }
                         }
            },0,20);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }

    private void startRTM() throws IOException, URISyntaxException, DeploymentException {
        URL url = new URL("https://www.slack.com/api/rtm.start?token="+BOT_TOKEN);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.connect();
        JSONObject returnVal = (JSONObject) JSONValue.parse(new InputStreamReader((InputStream) conn.getContent()));
        String wsUrl = (String) returnVal.get("url");

        //unescape the wsUrl string's slashes
        wsUrl = wsUrl.replace("\\","");

        //Query the users so we can link user id -> username
        System.out.println("Querying slack users..");
        UserList.queryUsers();

        System.out.println("Recieved WebSocket URI from slack, connecting... " + wsUrl);

        //Connect via the real-time messaging client and the websocket.
        ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
        client = ClientManager.createClient();
        rtm = new RTMClient();
        client.connectToServer(rtm,cec,new URI(wsUrl));
    }

    /**
     * Get the plugin's RTM client
     * @return the RTM client
     */
    public static RTMClient getRTM() {
        return rtm;
    }

}
