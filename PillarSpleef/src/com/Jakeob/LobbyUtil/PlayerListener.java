package com.Jakeob.LobbyUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
	private Lobby lobby;
	
	public PlayerListener(Lobby lobby) {
		this.lobby = lobby;
	}
	
	//prevents player from being hurt if waiting in lobby
	@EventHandler
	public void onPlayerHurt(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(lobby.isPlayerInLobby((Player)event.getEntity()) && !lobby.isPlayerInGame((Player)event.getEntity())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(lobby.getPlayers().contains(player)) {
			lobby.removePlayer(player);
		}
	}
	
	public void onPlayerKicked(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if(lobby.getPlayers().contains(player)) {
			lobby.removePlayer(player);
		}
	}
}