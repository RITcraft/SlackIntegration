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
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types.HelloMessage;
import edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types.MsgMessage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.websocket.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Class to streamline the usage for the websocket interface that slack
 * provides us for recieving chat messages from slack
 */
public class RTMClient extends Endpoint {

    Session session; //Session, not used since we use the slack web api to send messages
    public boolean dc = false; //Changed to true if the socket dies.
    /**
     * Send a slack message to a channel via the web api
     * @param text The text to send to the channel
     * @param channel The channel ID to send it to (usually the relay channel)
     */
    public void sendSlackMessage(String text, String channel) {
        try {
            URL chat = new URL("https://slack.com/api/chat.postMessage?token="+ SlackIntegration.BOT_TOKEN +"&channel="+channel+"&text="+ URLEncoder.encode(text,"UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) chat.openConnection();
            conn.connect();
            conn.getContent();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Called by superclass when the websocket closes
     * @param session The websocket session that is closing
     * @param reason The reason why the connection closed
     */
    @Override
    public void onClose(Session session, CloseReason reason) {
        dc = true;
    }

    /**
     * Called by superclass when some error happens with the websocket
     * @param sess The session that the websocket that errored was attached to
     * @param thr Throwable containing the error
     */
    @Override
    public void onError(Session sess, Throwable thr) {
        dc = true;
    }

    /**
     * Method for when the websocket opens. We set up the message listener here too
     * @param session The websocket session that just opened for this method
     * @param endpointConfig The endpoint configuration - not used
     */
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        RTMClient.this.session = session;
        System.out.println("Websocket connection open");

        //Register the message handler - This has to be a MessageHandler.Partial. If it is a MessageHandler.Whole it won't work.
        session.addMessageHandler(new MessageHandler.Partial<String>() {
            @Override
            public void onMessage(String s, boolean b) {
                JSONObject obj = (JSONObject) JSONValue.parse(s);
                if(obj.get("type") != null) {
                    String type = String.valueOf(obj.get("type"));

                    //Give the message off to the handler based on the event type
                    switch(type) {
                        case "hello":
                            HelloMessage.handle();
                            break;
                        case "message":
                            MsgMessage.handle(obj);
                            break;
                    }
                }else{
                    System.out.println("[Slack] Recieved malformed JSON message from slack - no type");
                }
            }
        });
    }
}
