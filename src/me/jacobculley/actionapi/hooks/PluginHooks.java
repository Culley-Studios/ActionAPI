// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi.hooks;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.Bukkit;
import net.milkbowl.vault.economy.Economy;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.jacobculley.actionapi.ActionAPI;

public class PluginHooks
{
    private ActionAPI plugin;
    protected static PlaceholderAPIPlugin placeholderAPI;
    protected static Economy econ;
    
    static {
        PluginHooks.placeholderAPI = null;
        PluginHooks.econ = null;
    }
    
    public PluginHooks() {
        this.plugin = ActionAPI.getInstance();
    }
    
    public void hookAll() {
        if (this.hookPlaceholderAPI()) {
            this.plugin.getLogger().info("ActionAPI has hooked into PlaceholderAPI");
        }
        if (this.setupEconomy()) {
            this.plugin.getLogger().info("ActionAPI has hooked into Vault");
        }
    }
    
    private boolean hookPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            PluginHooks.placeholderAPI = (PlaceholderAPIPlugin)Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        }
        return PluginHooks.placeholderAPI != null;
    }
    
	private boolean setupEconomy(){
    	if(plugin.getServer().getPluginManager().getPlugin("Vault") == null){
    		return false;
    	}
        
    	RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
    	if(rsp == null) {
    		return false;
    	}
    	econ = rsp.getProvider();
    	return econ != null;
    }
    
    public PlaceholderAPIPlugin getPlaceholderAPI() {
        return PluginHooks.placeholderAPI;
    }
    
    public Economy getEcon() {
        return PluginHooks.econ;
    }
}
