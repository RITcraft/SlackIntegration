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
package edu.rit.chrisbitler.ritcraft.slackintegration.listeners;

import edu.rit.chrisbitler.ritcraft.slackintegration.SlackIntegration;
import edu.rit.chrisbitler.ritcraft.slackintegration.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Class for listening to player events from the spigot server
 */
public class PlayerListener implements Listener {
    SlackIntegration plugin; //The plugin itself, used for getting access to the real-time messaging client

    /**
     * Create a new listener for player-based events
     * @param plugin The plugin this listener is being added to
     */
    public PlayerListener(SlackIntegration plugin) {
        this.plugin = plugin;
    }

    /**
     * Event called by spigot when a player chats
     * This sends the message over slack to show that a player chatted ingame so that the bot says
     * they chatted in the channel
     * @param e The player chat event.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(plugin.adminChat == null) {
            SlackIntegration.getRTM().sendSlackMessage("[" + e.getPlayer().getName() + "]: " + ChatColor.stripColor(e.getMessage()), SlackIntegration.RELAY_CHANNEL);
        }else{
            if(plugin.adminChat.toggledPlayers.contains(e.getPlayer().getName())) {
                SlackIntegration.getRTM().sendSlackMessage("[ADMIN][" + e.getPlayer().getName() + "]: " + ChatColor.stripColor(e.getMessage()), SlackIntegration.ADMIN_CHANNEL);
            }else{
                SlackIntegration.getRTM().sendSlackMessage("[" + e.getPlayer().getName() + "]: " + ChatColor.stripColor(e.getMessage()), SlackIntegration.RELAY_CHANNEL);
            }
        }
    }

    /**
     * Event called by spigot when a player uses a command, before it's processed
     * We use this to intercept the AdminPrivateChat messages and show them in the admin channel
     * @param e The command event
     */
    @EventHandler
    public void onCommandPre(PlayerCommandPreprocessEvent e) {
        if(e.getMessage().toLowerCase().startsWith("/amsg") || e.getMessage().toLowerCase().startsWith("/a")) {
            if(e.getPlayer().hasPermission("adminchat.send")) {
                String[] parts = e.getMessage().split(" ");
                String text = Util.combineArray(parts, 1);
                SlackIntegration.getRTM().sendSlackMessage("[ADMIN][" + e.getPlayer().getName() + "]: " + ChatColor.stripColor(text), SlackIntegration.ADMIN_CHANNEL);
            }
        }
    }

}
