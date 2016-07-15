// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi;

import me.jacobculley.actionapi.compatability.Compatability_1_10_R1;
import me.jacobculley.actionapi.compatability.Compatability_1_9_R2;
import me.jacobculley.actionapi.compatability.Compatability_1_9_R1;
import me.jacobculley.actionapi.compatability.Compatability_1_8_R3;
import me.jacobculley.actionapi.compatability.Compatability_1_8_R2;
import me.jacobculley.actionapi.compatability.Compatability_1_8_R1;
import me.jacobculley.actionapi.compatability.Compatability_1_7_R4;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import me.jacobculley.actionapi.action.Action;
import me.jacobculley.actionapi.hooks.PluginHooks;
import me.jacobculley.actionapi.compatability.CompatabilityManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionAPI extends JavaPlugin
{
    private static ActionAPI instance;
    private CompatabilityManager compatability;
    private PluginHooks PluginHooks;
    private Action Action;
    
    public void onEnable() {
        (ActionAPI.instance = this).getHooks();
        this.loadCommands();
        this.setupCompatability();
        this.Action = new Action();
    }
    
    public void onDisable() {
    }
    
    public void reload() {
        this.Action = new Action();
    }
    
    private void loadCommands() {
        this.getCommand("actionapi").setExecutor((CommandExecutor)new ActionCommand());
    }
    
    public String placeholderMessage(final Player p, String message) {
        message = PlaceholderAPI.setPlaceholders(p, message);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }
    
    public void sms(final Player p, String message) {
        if (p == null || message == null || message.isEmpty()) {
            return;
        }
        message = this.placeholderMessage(p, message);
        p.sendMessage(message);
    }
    
    private boolean setupCompatability() {
        String str;
        try {
            str = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
            return false;
        }
        if (str.equals("v1_7_R4")) {
            this.compatability = new Compatability_1_7_R4();
        }
        else if (str.equals("v1_8_R1")) {
            this.compatability = new Compatability_1_8_R1();
        }
        else if (str.equals("v1_8_R2")) {
            this.compatability = new Compatability_1_8_R2();
        }
        else if (str.equals("v1_8_R3")) {
            this.compatability = new Compatability_1_8_R3();
        }
        else if (str.equals("v1_9_R1")) {
            this.compatability = new Compatability_1_9_R1();
        }
        else if (str.equals("v1_9_R2")) {
            this.compatability = new Compatability_1_9_R2();
        }
        else if (str.equals("v1_10_R1")) {
            this.compatability = new Compatability_1_10_R1();
        }
        return this.compatability != null;
    }
    
    public PluginHooks getHooks() {
        if (this.PluginHooks == null) {
            (this.PluginHooks = new PluginHooks()).hookAll();
        }
        return this.PluginHooks;
    }
    
    public CompatabilityManager getCompatability() {
        return this.compatability;
    }
    
    public static ActionAPI getInstance() {
        return ActionAPI.instance;
    }
    
    public Action getAPI() {
        return this.Action;
    }
}
