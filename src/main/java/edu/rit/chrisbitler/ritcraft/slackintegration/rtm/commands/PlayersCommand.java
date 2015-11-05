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

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Command to show the number and list of players in a slack channel
 */
public class PlayersCommand extends Command {

    /**
     * Register the players command in all channels
     */
    public PlayersCommand() {
        super("players","all");
    }

    /**
     * Process the players command to show the number and list of all players
     * @param channel The channel the command was used in
     * @param args The arguments of the command (not used here)
     */
    @Override
    public void processCommand(String channel, String[] args) {
        String players = "Players ("+ Bukkit.getOnlinePlayers().size() +"): [";
        boolean first = true;
        for(Player p :Bukkit.getOnlinePlayers()) {
            if(first) {
                players += (ChatColor.stripColor(p.getDisplayName()));
                first = false;
            }else{
                players += (", " + ChatColor.stripColor(p.getDisplayName()));
            }
        }
        players+="]";
        SlackIntegration.getRTM().sendSlackMessage(players,channel);
    }
}
