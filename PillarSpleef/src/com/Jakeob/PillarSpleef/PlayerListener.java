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
	private PillarSpleef plugin;
	private Lobby lobby;
	
	public PlayerListener(PillarSpleef plugin, Lobby lobby) {
		this.plugin = plugin;
		this.lobby = lobby;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		lobby.joinPlayer(player);
		player.sendMessage(ChatColor.GREEN + "Joined Lobby!");
		
		//TODO: Check if they're in config or not already, if not: add them with 0 score
		if(!plugin.isPlayerInConfig(player)) {
			plugin.setPlayerPoints(player, 0);
		}
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