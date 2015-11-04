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
