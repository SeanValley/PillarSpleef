package com.Jakeob.PillarSpleef;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Jakeob.LobbyUtil.Lobby;

public class PlayerListener implements Listener{
	private Lobby lobby;
	
	public PlayerListener(Lobby lobby) {
		this.lobby = lobby;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		lobby.addPlayer(player);
		player.sendMessage(ChatColor.GREEN + "Joined Lobby!");
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event) {
		if(!event.getCause().equals(DamageCause.LAVA)) {
			event.setDamage(0);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}
}