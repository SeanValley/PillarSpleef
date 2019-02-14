package com.Jakeob.PillarSpleef.GameProcedures;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.Jakeob.LobbyUtil.Helper;
import com.Jakeob.LobbyUtil.Lobby;
import com.Jakeob.LobbyUtil.SpawnProcedure;

public class SpawnPlayerProcedure implements SpawnProcedure{
	private Lobby lobby;
	
	@Override
	public void run(ArrayList<Player> players) {
		for(Player player : players) {
			Helper.spawnPlayer(player, lobby.getSpawn(0).getSpawnCorner1(), lobby.getSpawn(0).getSpawnCorner2());
			player.getInventory().clear();
		}
	}
	
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
}