package com.Jakeob.PillarSpleef;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Jakeob.LobbyUtil.Lobby;

public class GameOverListener implements Listener{
	private Lobby lobby;
	
	
	public GameOverListener(Lobby lobby) {
		this.lobby = lobby;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		removePlayerIfInGame(event.getEntity());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		removePlayerIfInGame(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent event) {
		removePlayerIfInGame(event.getPlayer());
	}
	
	private void removePlayerIfInGame(Player player) {
		if(lobby.isPlayerInGame(player)) {
			lobby.removePlayerFromGame(player);
			if(lobby.getPlayersInGame().size() == 1) {
				gameOver(lobby.getPlayersInGame().get(0));
			}else if(lobby.getPlayersInGame().size() == 0) {
				gameOver(null);
			}
		}
	}
	
	private void gameOver(Player winner) {
		if(winner != null) {
			String winnerName = winner.getDisplayName();
			
			winner.teleport(lobby.getLobbyLoc());
			winner.setHealth(20);
			winner.setFoodLevel(20);
			winner.setFireTicks(0);
			winner.sendMessage(ChatColor.GREEN + "You win! Congratulations!");
			
			for(Player p : this.lobby.getPlayers()) {
				p.sendMessage(ChatColor.YELLOW + winnerName + " has won the game!");
			}
			lobby.gameComplete();
		}else {
			for(Player p : this.lobby.getPlayers()) {
				p.sendMessage(ChatColor.YELLOW + "Nobody won this time!");
			}
			lobby.gameComplete();
		}
	}
}