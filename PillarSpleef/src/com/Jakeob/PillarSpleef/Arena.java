package com.Jakeob.PillarSpleef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;

public class Arena {
	private Random random;
	
	private World world;
	private int size;
	private int height;
	
	private int[][] pillars;
	
	private AutoSpleef autoSpleefer;
	private final ArrayList<Material> materials = new ArrayList<Material>(Arrays.asList(Material.BRICK, Material.GLASS, Material.JACK_O_LANTERN, Material.GLOWSTONE, Material.DIAMOND_BLOCK, Material.LAPIS_BLOCK, Material.SOUL_SAND, Material.SPONGE, Material.ICE));
	
	public Arena(PillarSpleef plugin, int size, int height) {
		this.random = new Random();
		this.world = plugin.getServer().getWorld("world");
		this.size = size;
		this.height = height;
		
		this.pillars = new int[size+1][size+1];
		for(int x=0;x<size+1;x++) {
			for(int z=0;z<size+1;z++) {
				this.pillars[x][z] = height;
			}
		}
		
		this.autoSpleefer = new AutoSpleef(this);
		
		this.autoSpleefer.runTaskTimer(plugin, 60, 60);
	}
	
	public void generateArena() {
		/*
		 *Arena Layout:
		 *  Play area is between (0, 0) and (size, size)
		 *  The initial pillar heights are set to height
		 *  Underneath the play area, lava and a floor are placed 
		 */
		
		//place arena walls
		//walls are built around the play area
		//walls are built 5 blocks higher than pillar height to prevent players jumping out
		fillArea(Material.BEDROCK, -1, 0, -1, this.size + 1, this.height + 7, -1);
		fillArea(Material.BEDROCK, -1, 0, -1, -1, this.height + 7, this.size + 1);
		fillArea(Material.BEDROCK, -1, 0, this.size + 1, this.size + 1, this.height + 7, this.size + 1);
		fillArea(Material.BEDROCK, this.size + 1, 0, -1, this.size + 1, this.height + 7, this.size + 1);
		
		//place arena ground
		fillArea(Material.BEDROCK, 0, 0, 0, this.size, 0, this.size);
		fillArea(Material.LAVA, 0, 1, 0, this.size, 1, this.size);
		
		//fill arena with blocks
		fillArea(getRandomMaterial(), 0, 2, 0, this.size, this.height + 2, this.size);
	}
	
	public void startSpleef() {
		this.autoSpleefer.toggleSpleefingOn();
	}
	
	public void stopSpleef() {
		this.autoSpleefer.toggleSpleefingOff();
	}
	
	public void resetArena() {
		fillArea(getRandomMaterial(), 0, 2, 0, this.size, this.height + 2, this.size);
		
		for(int x=0;x<size+1;x++) {
			for(int z=0;z<size+1;z++) {
				pillars[x][z] = height;
			}
		}
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getHeight() {
		return this.getHeight();
	}
	
	public int[][] getPillarStates(){
		return this.pillars;
	}
	
	public void setPillarStates(int[][] pillars) {
		this.pillars = pillars;
	}
	
	private Material getRandomMaterial() {
		int randInt = this.random.nextInt(this.materials.size());
		return this.materials.get(randInt);
	}
	
	private void fillArea(Material mat, int xi, int yi, int zi, int xf, int yf, int zf) {
		for(int x=xi;x<=xf;x++) {
			for(int y=yi;y<=yf;y++) {
				for(int z=zi;z<=zf;z++) {
					this.world.getBlockAt(x, y, z).setType(mat);
				}
			}
		}
	}
}