/**
 * @author Chris Bitler
 */
package edu.rit.chrisbitler.ritcraft.slackintegration.rtm.types;

/**
 * Simple class for acknowledging that we connected to the slack websocket api
 */
public class HelloMessage {
    /**
     * Print out a message showing we successfully connected
     */
    public static void handle() {
        System.out.println("Succesfully connected to Slack RTM Api");
    }
}
