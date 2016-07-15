// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi;

import org.bukkit.configuration.file.FileConfiguration;

public class SetConfig
{
    private ActionAPI plugin;
    
    public SetConfig() {
        this.plugin = ActionAPI.getInstance();
    }
    
    public void setHeader() {
        final FileConfiguration cf = this.plugin.getConfig();
        final String version = this.plugin.getDescription().getVersion();
        cf.options().header("======================================================\n\nActionAPI Version: " + version + "\nCreated by: JC_Plays_MC" + "\n" + "\n======================================================" + "\n" + "\nThis is the ActionAPI configuration file where all general settings" + "\nand other plugin toggles will be found. If you have any questions feel free" + "\nto ask me preferably by PM on spigot. Also if you have any feature suggestions" + "\nor anything like that i'm always open to new ideas." + "\n" + "\n======================================================" + "\n");
        this.plugin.saveConfig();
    }
}
