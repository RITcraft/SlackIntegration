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
package edu.rit.chrisbitler.ritcraft.slackintegration.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Utility class
 */
public class Util {

    /**
     * Utility method for getting the plugin message prefix
     * @return the plugin message prefix
     */
    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Slack" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
    }

    /**
     * Utility method to broadcast to players with the slack prefix.
     * @param msg The message to broadcast
     */
    public static void broadcast(String msg) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(getPrefix() + msg);
        }
    }

    /**
     * Combines the parts of a string array into a statement seperated by spaces
     * @param array The array to combine
     * @param offset The starting offset in the array to start combining at.
     * @return
     */
    public static String combineArray(String[] array, int offset) {
        String combination = "";
        for(int i = offset; i < array.length; i++) {
            combination+=(array[i]+" ");
        }
        return combination;
    }

    /**
     * Shift an array forward a number of elements
     * @param array The array that you want to shift
     * @param offset The offset that you want to start on
     * @return The shifted array
     */
    public static String[] shiftArray(String[] array, int offset) {
        String[] newArray = new String[array.length-offset];
        int j = 0;
        for(int i = offset; i < array.length; i++) {
            newArray[j] = array[i];
            j++;
        }
        return newArray;
    }

}
