package me.jacobculley.actionapi.compatability;

import java.util.Collection;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Compatability_1_9_R2 implements CompatabilityManager {
	
	public String getVersion(){
		return "1_9_R2";
	}

	public void sendTitle(Player p, String title) {
		
		if(p == null || title == null || title.isEmpty()){
			return;
		}
		
		IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
			
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleComponent);
		
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendSubtitle(Player p, String subtitle) {
		
		if(p == null || subtitle == null || subtitle.isEmpty()){
			return;
		}
		
		IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
			
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleComponent);
		
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendAction(Player p, String message) {
		
		if(p == null || message == null || message.isEmpty()){
			return;
		}
		
		IChatBaseComponent actionMessage = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		
		PacketPlayOutChat packet = new PacketPlayOutChat(actionMessage, (byte) 2);
			
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendJSONMessage(Player p, String message) {
		
		if(p == null || message == null || message.isEmpty()){
			return;
		}
		
		String[] json = message.split("&&");

		IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(json[0]);
		
		if(json.length > 1){
			for(int i = 1; i < json.length; i++){
				
				IChatBaseComponent addition = IChatBaseComponent.ChatSerializer.a(json[i]);
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

		IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(json[0]);
		
		if(json.length > 1){
			for(int i = 1; i < json.length; i++){
				
				IChatBaseComponent addition = IChatBaseComponent.ChatSerializer.a(json[i]);
				component = component.addSibling(addition);
			}
		}
				
		PacketPlayOutChat packet = new PacketPlayOutChat(component, (byte) 0);
		
		for(Player p : players){
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
