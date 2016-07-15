// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ActionCommand implements CommandExecutor
{
    private ActionAPI plugin;
    
    public ActionCommand() {
        this.plugin = ActionAPI.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("actionapi")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (args.length == 0) {
            if (!p.hasPermission("actionapi.admin")) {
                this.plugin.sms(p, "&8&m---------------------------");
                this.plugin.sms(p, "&bAction&fAPI &7v&f" + this.plugin.getDescription().getVersion());
                this.plugin.sms(p, "&7By: &aJC_Plays_MC");
                this.plugin.sms(p, "&8&m---------------------------");
                return true;
            }
            this.plugin.sms(p, "&8&m----------------------------");
            this.plugin.sms(p, "&bActionAPI");
            this.plugin.sms(p, "&a/actionapi help - &fDisplays this help message");
            this.plugin.sms(p, "&a/actionapi reload - &fReloads the plugin");
            this.plugin.sms(p, "&8&m----------------------------");
            return true;
        }
        else if (args.length == 1) {
            if (!p.hasPermission("actionapi.admin")) {
                this.plugin.sms(p, "&cYou do not have permission to execute this command");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                this.plugin.sms(p, "&8&m----------------------------");
                this.plugin.sms(p, "&bActionAPI");
                this.plugin.sms(p, "&a/actionapi help - &fDisplays this help message");
                this.plugin.sms(p, "&a/actionapi reload - &fReloads the plugin");
                this.plugin.sms(p, "&8&m----------------------------");
                return false;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                this.plugin.reload();
                this.plugin.sms(p, "&8&m----------------------------");
                this.plugin.sms(p, "&bActionAPI &7has been reloaded!");
                this.plugin.sms(p, "&8&m----------------------------");
                return false;
            }
            this.plugin.sms(p, "&cUnkown command, use /actionapi help");
            return true;
        }
        else {
            if (!p.hasPermission("actionapi.admin")) {
                this.plugin.sms(p, "&cYou do not have permission to execute this command");
                return true;
            }
            this.plugin.sms(p, "&cUnkown command, use /actionapi help");
            return false;
        }
    }
}
