package me.jacobculley.actionapi.compatability;

import java.util.Collection;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.jacobculley.actionapi.ActionAPI;

public class Compatability_1_8_R1 implements CompatabilityManager {
	
	public String getVersion(){
		return "1_8_R1";
	}

	public void sendTitle(Player p, String title) {
		ActionAPI.getInstance().getLogger().info("Cannot send titles using 1.8.0");
	}

	public void sendSubtitle(Player p, String subtitle) {
		ActionAPI.getInstance().getLogger().info("Cannot send titles using 1.8.0");
	}

	public void sendAction(Player p, String message) {
		
		if(p == null || message == null || message.isEmpty()){
			return;
		}
		
		IChatBaseComponent actionMessage = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		
		PacketPlayOutChat packet = new PacketPlayOutChat(actionMessage, (byte) 2);
			
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendJSONMessage(Player p, String message) {
		
		if(p == null || message == null || message.isEmpty()){
			return;
		}
		
		String[] json = message.split("&&");

		IChatBaseComponent component = ChatSerializer.a(json[0]);
		
		if(json.length > 1){
			for(int i = 1; i < json.length; i++){
				
				IChatBaseComponent addition = ChatSerializer.a(json[i]);
				component = component.addSibling(addition);
			}
		}
				
		PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) 0);
			
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendJSONBroadcast(Collection<? extends Player> players, String message) {
		
		if(players == null || message == null || message.isEmpty()){
			return;
		}
		
		String[] json = message.split("&&");

		IChatBaseComponent component = ChatSerializer.a(json[0]);
		
		if(json.length > 1){
			for(int i = 1; i < json.length; i++){
				
				IChatBaseComponent addition = ChatSerializer.a(json[i]);
				component = component.addSibling(addition);
			}
		}
				
		PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) 0);
		
		for(Player p : players){
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
