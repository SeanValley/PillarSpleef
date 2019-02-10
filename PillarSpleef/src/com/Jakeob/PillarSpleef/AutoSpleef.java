package com.Jakeob.PillarSpleef;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSpleef extends BukkitRunnable{

	private Random random;
	
	private Arena arena;
	private boolean isSpleefing;
	
	public AutoSpleef(Arena arena) {
		this.random = new Random();
		
		this.arena = arena;
		this.isSpleefing = false;
	}
	
	public void toggleSpleefingOn() {
		this.isSpleefing = true;
	}
	
	public void toggleSpleefingOff() {
		this.isSpleefing = false;
	}
	
	@Override
	public void run() {
		if(this.isSpleefing) {
			int[][] pillars = arena.getPillarStates();
			for(int x=0;x<arena.getSize()+1;x++) {
				for(int z=0;z<arena.getSize()+1;z++) {
					//50:50 chance of pillar decreasing 1 in size
					int randInt = random.nextInt(2);
					if(randInt == 0) {
						int pillarHeight = pillars[x][z] - 1;
						if(pillarHeight > -2) {
							pillars[x][z] = pillarHeight;
							arena.getWorld().getBlockAt(x, pillarHeight + 3, z).setType(Material.AIR);
						}
					}
				}
			}
			
			arena.setPillarStates(pillars);
		}
	}
}