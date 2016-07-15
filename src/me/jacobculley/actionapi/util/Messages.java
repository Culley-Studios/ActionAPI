// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.jacobculley.actionapi.ActionAPI;

public class Messages
{
    private ActionAPI plugin;
    
    public Messages() {
        this.plugin = ActionAPI.getInstance();
    }
    
    public void sms(final Player p, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        message = this.setPlaceholders(p, message);
        p.sendMessage(message);
    }
    
    public void info(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        message = this.setColour(message);
        message = ChatColor.stripColor(message);
        this.plugin.getLogger().info(message);
    }
    
    public String setColour(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }
        message = ChatColor.translateAlternateColorCodes('§', message);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }
    
    public String setPlaceholders(final OfflinePlayer p, String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        message = this.setColour(message);
        if (!message.contains("%")) {
            return message;
        }
        message = message.replace("%server_motd%", new StringBuilder().append(Bukkit.getServer().getMotd()).toString());
        message = message.replace("%server_maxplayers%", new StringBuilder().append(Bukkit.getServer().getMaxPlayers()).toString());
        message = message.replace("%server_playercount%", new StringBuilder().append(Bukkit.getServer().getOnlinePlayers().size()).toString());
        if (p != null) {
            message = message.replace("%player%", p.getName());
        }
        if (this.plugin.getHooks().getPlaceholderAPI() != null && p.isOnline() && p.getPlayer() != null) {
            message = PlaceholderAPI.setPlaceholders(p.getPlayer(), message);
        }
        return message;
    }
    
    public String setPlaceholders(final Player p, String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        message = this.setColour(message);
        if (!message.contains("%")) {
            return message;
        }
        message = message.replace("%server_motd%", new StringBuilder().append(Bukkit.getServer().getMotd()).toString());
        message = message.replace("%server_maxplayers%", new StringBuilder().append(Bukkit.getServer().getMaxPlayers()).toString());
        message = message.replace("%server_playercount%", new StringBuilder().append(Bukkit.getServer().getOnlinePlayers().size()).toString());
        if (p != null) {
            message = message.replace("%player%", p.getName());
            message = message.replace("%player_displayname%", p.getDisplayName());
            message = message.replace("%player_saturation%", new StringBuilder().append(p.getSaturation()).toString());
            message = message.replace("%player_hunger%", new StringBuilder().append(p.getFoodLevel()).toString());
            message = message.replace("%player_health%", new StringBuilder().append(p.getHealth()).toString());
            message = message.replace("%player_x%", new StringBuilder().append(p.getLocation().getBlockX()).toString());
            message = message.replace("%player_y%", new StringBuilder().append(p.getLocation().getBlockY()).toString());
            message = message.replace("%player_z%", new StringBuilder().append(p.getLocation().getBlockZ()).toString());
            message = message.replace("%player_world%", new StringBuilder().append(p.getWorld().getName()).toString());
            message = message.replace("%player_level%", new StringBuilder().append(p.getLevel()).toString());
            message = message.replace("%player_exp%", new StringBuilder().append(p.getExp()).toString());
        }
        if (this.plugin.getHooks().getPlaceholderAPI() != null) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }
}
