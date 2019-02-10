package com.Jakeob.LobbyUtil;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Queue extends BukkitRunnable{
	private Lobby lobby;
	private GameProcedure gameStart;
	
	public Queue(Lobby lobby, GameProcedure gameStart) {
		this.lobby = lobby;
		this.gameStart = gameStart;
	}
	
	
	//replaced this.lobby.getPlayers() with livePlayers and added the initialization at the top
	@Override
	public void run() {
		if(this.lobby.isGameOpen()) {
			ArrayList<Player> livePlayers = new ArrayList<Player>();
			for(Player player : this.lobby.getPlayers()) {
				if(!player.isDead() && player.isOnline()) {
					livePlayers.add(player);
				}
			}
			
			if(livePlayers.size() >= this.lobby.getMinPlayers()) {
				if(livePlayers.size() <= this.lobby.getMaxPlayers()) {
					for(Player player : livePlayers) {
						Helper.spawnPlayer(player, lobby.getSpawnCorner1(), lobby.getSpawnCorner2());
						player.sendMessage(ChatColor.GREEN + "Game started, good luck!");
						this.lobby.addPlayerToGame(player);
					}
				}else {
					//Spawn players up to the max players
					for(int i=0;i<this.lobby.getMaxPlayers();i++) {
						Helper.spawnPlayer(livePlayers.get(i), this.lobby.getSpawnCorner1(), this.lobby.getSpawnCorner2());
						livePlayers.get(i).sendMessage(ChatColor.GREEN + "Game started, good luck!");
						this.lobby.addPlayerToGame(livePlayers.get(i));
					}
					
					//Move players up in the queue
					@SuppressWarnings("unchecked")
					ArrayList<Player> currentLobby = (ArrayList<Player>) this.lobby.getPlayers().clone();
					for(int i=this.lobby.getMaxPlayers();i<currentLobby.size();i++) {
						this.lobby.clearPlayers();
						this.lobby.addPlayer(currentLobby.get(i));
						currentLobby.get(i).sendMessage(ChatColor.RED + "Too many players in lobby, you have been moved up in the queue.");
					}
					
					for(int i=0;i<this.lobby.getMaxPlayers();i++) {
						this.lobby.addPlayer(currentLobby.get(i));
					}
				}
				
				gameStart.run();
				this.lobby.setGameOpen(false);
			}else {
				for(Player player : livePlayers) {
					player.sendMessage(ChatColor.RED + "Waiting for more players...");
				}
			}
		}else {
			for(Player player : this.lobby.getPlayers()) {
				if(!this.lobby.isPlayerInGame(player)) {
					player.sendMessage(ChatColor.RED + "Waiting for open game...");
				}
			}
		}
	}
}