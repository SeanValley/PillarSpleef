package com.Jakeob.LobbyUtil;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Spawn {
	private Location spawnCorner1;
	private Location spawnCorner2;
	
	public Spawn(Location spawnCorner1, Location spawnCorner2) {
		this.spawnCorner1 = spawnCorner1;
		this.spawnCorner2 = spawnCorner2;
	}
	
	public Location getSpawnCorner1() {
		return this.spawnCorner1;
	}
	
	public Location getSpawnCorner2() {
		return this.spawnCorner2;
	}
	
	public void spawnPlayer(Player player) {
		Helper.spawnPlayer(player, this.spawnCorner1, this.spawnCorner2);
	}
}
