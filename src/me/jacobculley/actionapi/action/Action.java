// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi.action;

import com.google.common.io.ByteArrayDataOutput;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.World;
import javax.script.ScriptEngine;
import com.google.common.io.ByteStreams;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import javax.script.ScriptException;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import me.jacobculley.actionapi.util.Messages;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.List;
import org.bukkit.entity.Player;
import me.jacobculley.actionapi.ActionAPI;

public class Action
{
    private ActionAPI plugin;
    
    public Action() {
        this.plugin = ActionAPI.getInstance();
    }
    
    public void executeActions(final Player p, final List<String> actions) {
        if (actions == null || actions.isEmpty()) {
            return;
        }
        for (final String action : actions) {
            this.executeAction(p, action);
        }
    }
    
    public boolean executeAction(final Player p, String action) {
        int delayTimer = 0;
        int chance = 100;
        if (action.contains("[Chance=")) {
            for (int i = 1; i <= 100; ++i) {
                if (action.contains("[Chance=" + i + "]")) {
                    chance = i;
                    action = action.replace("[Chance=" + i + "] ", "").replace("[Chance=" + i + "]", "");
                }
            }
        }
        if (action.contains("[Delay=")) {
            for (int i = 1; i < 61; ++i) {
                if (action.contains("[Delay=" + i + "]")) {
                    delayTimer = i * 20;
                    action = action.replace("[Delay=" + i + "] ", "").replace("[Delay=" + i + "]", "");
                }
            }
        }
        if (action.contains("[Delay=1000]")) {
            action = action.replace("[Delay=1000]", "");
            delayTimer = 2;
        }
        final String runAction = action;
        if (chance != 100) {
            final double chanceCheck = Math.random() * 100.0;
            if (chanceCheck > chance) {
                return false;
            }
        }
        if (delayTimer != 0) {
            Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    Action.this.runAction(p, runAction);
                }
            }, (long)delayTimer);
        }
        else {
            this.runAction(p, runAction);
        }
        return false;
    }
    
    private void runAction(final Player p, String action) {
        action = action.replace("[Delay=0]", "").replace("[Delay=0]", "");
        final Messages Messages = new Messages();
        if (action.contains("[JavaScript=")) {
            final HashMap<String, String> scripts = new HashMap<String, String>();
            String script = null;
            final ScriptEngineManager manager = new ScriptEngineManager();
            final ScriptEngine engine = manager.getEngineByName("js");
            engine.put("BukkitPlayer", p);
            engine.put("ActionAPI", this.plugin);
            engine.put("PlayerCommand", "[PlayerCommand]");
            engine.put("ConsoleCommand", "[ConsoleCommand]");
            engine.put("OperatorCommand", "[OperatorCommand]");
            engine.put("Message", "[Message]");
            engine.put("Broadcast", "[Broadcast]");
            engine.put("Sound", "[Sound]");
            engine.put("VaultGive", "[VaultGive]");
            engine.put("VaultTake", "[VaultTake]");
            engine.put("Teleport", "[Teleport]");
            engine.put("GiveItem", "[GiveItem]");
            engine.put("Title", "[Title]");
            engine.put("ActionBar", "[ActionBar]");
            engine.put("JSONMessage", "[JSONMessage]");
            engine.put("JSONBroadcast", "[JSONBroadcast]");
            engine.put("Bungee", "[Bungee]");
            for (int i = 0; i < action.length(); ++i) {
                if (action.charAt(i) == '[' && action.substring(i, i + 12).equals("[JavaScript=")) {
                    for (int e = i + 12; e < action.length(); ++e) {
                        if (action.charAt(e) == ']') {
                            final String orginalScript;
                            script = (orginalScript = action.substring(i + 12, e));
                            script = Messages.setPlaceholders(p, script);
                            String result = null;
                            try {
                                final Object obj = engine.eval(script);
                                if (obj != null) {
                                    result = obj.toString();
                                }
                            }
                            catch (ScriptException e2) {
                                e2.printStackTrace();
                            }
                            if (result != null && !result.isEmpty()) {
                                scripts.put(orginalScript, result);
                            }
                            e = action.length();
                        }
                    }
                }
            }
            if (scripts != null && !scripts.isEmpty()) {
                for (final String sc : scripts.keySet()) {
                    action = action.replace("[JavaScript=" + sc + "]", scripts.get(sc));
                }
            }
        }
        if (action.contains("[PlayerCommand]")) {
            action = Messages.setPlaceholders(p, action.replace("[PlayerCommand] ", "").replace("[PlayerCommand]", ""));
            p.performCommand(action);
        }
        else if (action.contains("[ConsoleCommand]")) {
            action = Messages.setPlaceholders(p, action.replace("[ConsoleCommand] ", "").replace("[ConsoleCommand]", ""));
            this.plugin.getServer().dispatchCommand((CommandSender)this.plugin.getServer().getConsoleSender(), action);
        }
        else if (action.contains("[OperatorCommand]")) {
            action = Messages.setPlaceholders(p, action.replace("[OperatorCommand] ", "").replace("[OperatorCommand]", ""));
            if (!p.isOp()) {
                p.setOp(true);
                this.plugin.getServer().dispatchCommand((CommandSender)p, action);
                p.setOp(false);
            }
            else {
                this.plugin.getServer().dispatchCommand((CommandSender)p, action);
            }
        }
        else if (action.contains("[Message]")) {
            action = ChatColor.translateAlternateColorCodes('&', Messages.setPlaceholders(p, action.replace("[Message] ", "").replace("[Message]", "")));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', action));
        }
        else if (action.contains("[Broadcast]")) {
            action = ChatColor.translateAlternateColorCodes('&', Messages.setPlaceholders(p, action.replace("[Broadcast] ", "").replace("[Broadcast]", "")));
            this.plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', action));
        }
        else if (action.contains("[Sound]")) {
            action = Messages.setPlaceholders(p, action.replace("[Sound] ", "").replace("[Sound]", ""));
            final float soundFloat = 1.0f;
            p.playSound(p.getLocation(), Sound.valueOf(action.toUpperCase()), soundFloat, soundFloat);
        }
        else if (action.contains("[VaultGive]")) {
            action = Messages.setPlaceholders(p, action.replace("[VaultGive] ", "").replace("[VaultGive]", ""));
            final int amount = Integer.parseInt(action);
            this.plugin.getHooks().getEcon().depositPlayer((OfflinePlayer)p, (double)amount);
        }
        else if (action.contains("[VaultTake]")) {
            action = Messages.setPlaceholders(p, action.replace("[VaultGive] ", "").replace("[VaultGive]", ""));
            final int amount = Integer.parseInt(action);
            this.plugin.getHooks().getEcon().withdrawPlayer((OfflinePlayer)p, (double)amount);
        }
        else if (action.contains("[Teleport]")) {
            action = Messages.setPlaceholders(p, action.replace("[Teleport] ", "").replace("[Teleport]", ""));
            final String[] location = action.split(";");
            Location destination = null;
            if (location.length == 4) {
                final World world = Bukkit.getWorld(location[0]);
                final double x = Double.parseDouble(location[1]);
                final double y = Double.parseDouble(location[2]);
                final double z = Double.parseDouble(location[3]);
                destination = new Location(world, x, y, z);
            }
            else if (location.length == 6) {
                final World world = Bukkit.getWorld(location[0]);
                final double x = Double.parseDouble(location[1]);
                final double y = Double.parseDouble(location[2]);
                final double z = Double.parseDouble(location[3]);
                final float yaw = Float.parseFloat(location[4]);
                final float pitch = Float.parseFloat(location[5]);
                destination = new Location(world, x, y, z, yaw, pitch);
            }
            if (location != null) {
                p.teleport(destination);
            }
        }
        else if (action.contains("[GiveItem]")) {
            action = Messages.setPlaceholders(p, action.replace("[GiveItem] ", "").replace("[GiveItem]", ""));
            final String[] item = action.split(";");
            ItemStack newItem = null;
            if (item.length == 1) {
                newItem = new ItemStack(Material.valueOf(item[0]), 1);
            }
            else if (item.length == 2) {
                newItem = new ItemStack(Material.valueOf(item[0]), Integer.parseInt(item[1]));
            }
            else if (item.length == 3) {
                newItem = new ItemStack(Material.valueOf(item[0]), Integer.parseInt(item[1]), (short)(byte)Integer.parseInt(item[2]));
            }
            else if (item.length == 4) {
                newItem = new ItemStack(Material.valueOf(item[0]), Integer.parseInt(item[1]), (short)(byte)Integer.parseInt(item[2]));
                final ItemMeta meta = newItem.getItemMeta();
                meta.setDisplayName(Messages.setPlaceholders(p, item[3]));
                newItem.setItemMeta(meta);
            }
            if (newItem != null) {
                if (p.getInventory().firstEmpty() < 0) {
                    p.getWorld().dropItemNaturally(p.getLocation(), newItem);
                }
                else {
                    p.getInventory().addItem(new ItemStack[] { newItem });
                }
            }
        }
        else if (action.contains("[Title]")) {
            action = Messages.setPlaceholders(p, action.replace("[Title] ", "").replace("[Title]", ""));
            final String[] titleString = action.split(";");
            if (titleString.length == 1 || titleString.length == 2) {
                this.plugin.getCompatability().sendTitle(p, Messages.setPlaceholders(p, titleString[0]));
                if (titleString.length == 2) {
                    this.plugin.getCompatability().sendSubtitle(p, Messages.setPlaceholders(p, titleString[1]));
                }
            }
        }
        else if (action.contains("[ActionBar]")) {
            action = Messages.setPlaceholders(p, action.replace("[ActionBar] ", "").replace("[ActionBar]", ""));
            this.plugin.getCompatability().sendAction(p, Messages.setPlaceholders(p, action));
        }
        else if (action.contains("[JSONMessage]")) {
            action = Messages.setPlaceholders(p, action.replace("[JSONMessage] ", "").replace("[JSONMessage]", ""));
            this.plugin.getCompatability().sendJSONMessage(p, action);
        }
        else if (action.contains("[JSONBroadcast]")) {
            action = Messages.setPlaceholders(p, action.replace("[JSONBroadcast] ", "").replace("[JSONBroadcast]", ""));
            this.plugin.getCompatability().sendJSONBroadcast(this.plugin.getServer().getOnlinePlayers(), action);
        }
        else if (action.contains("[Bungee]")) {
            action = Messages.setPlaceholders(p, action.replace("[Bungee] ", "").replace("[Bungee]", ""));
            final ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(action);
            p.sendPluginMessage((Plugin)this.plugin, "BungeeCord", out.toByteArray());
        }
    }
}
