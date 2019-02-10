package com.Jakeob.LobbyUtil;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Helper {
	public static Random random = new Random();
	
	public static void spawnPlayer(Player player, Location spawnCorner1, Location spawnCorner2) {
		int x1 = spawnCorner1.getBlockX();
		int y1 = spawnCorner1.getBlockY();
		int z1 = spawnCorner1.getBlockZ();
		
		int x2 = spawnCorner2.getBlockX();
		int y2 = spawnCorner2.getBlockY();
		int z2 = spawnCorner2.getBlockZ();
		
		Location spawn = null;
		
		//loops until it finds open spawn spot
		do {
			int xLoc = randNumBetween(x1, x2);
			int yLoc = randNumBetween(y1, y2);
			int zLoc = randNumBetween(z1, z2);
			
			spawn = new Location(spawnCorner1.getWorld(), xLoc, yLoc, zLoc);
		}while(!spawn.getBlock().getType().equals(Material.AIR));
		
		player.teleport(spawn);
	}
	
	public static int randNumBetween(int x1, int x2) {
		int higher = 0;
		int lower = 0;
		
		if(x1 > x2) {
			higher = x1;
			lower = x2;
		}else if(x2 > x1) {
			higher = x2;
			lower = x1;
		}else {
			return x1;
		}
		
		return random.nextInt((higher - lower) + 1) + lower;
	}
}