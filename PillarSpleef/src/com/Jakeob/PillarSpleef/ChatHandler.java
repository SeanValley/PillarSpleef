package com.Jakeob.PillarSpleef;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener{
	private PillarSpleef plugin;
	
	public ChatHandler(PillarSpleef plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		String newMessage = "[" + plugin.getPlayerPoints(event.getPlayer()) + "] " + event.getPlayer().getDisplayName() + ": " + event.getMessage();
		plugin.getServer().broadcastMessage(newMessage);
	}
}