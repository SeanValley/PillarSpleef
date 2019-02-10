package com.Jakeob.PillarSpleef;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Jakeob.LobbyUtil.Lobby;
import com.Jakeob.PillarSpleef.GameProcedures.StartProcedure;
import com.Jakeob.PillarSpleef.GameProcedures.StopProcedure;

public class PillarSpleef extends JavaPlugin{
	public Logger log;
	
	private Arena arena;
	private Lobby lobby;
	
	
	/**
	 * Pillar Spleef 1.0
	 * By: Sean Valley
	 * Email: valleydsean@gmail.com
	 * 
	 * Bukkit plugin that runs a minigame
	 * Players are put into a lobby upon joining & put into a game when one opens
	 * The arena is configurable in the settings
	 * The lobby and arena spawns can be set up via commands
	 * 
	 * The arena will slowly fall into a pit of lava, the last person to fall in wins!
	 */
	public void onDisable() {
		this.log.info("PillarSpleef 1.0 has been disabled!");
	}
	  
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		PluginManager pm = this.getServer().getPluginManager();
		
		loadConfiguration();

		String lobbyWorld = this.getConfig().getString("Lobby.World");
		int lobbyX = this.getConfig().getInt("Lobby.X");
		int lobbyY = this.getConfig().getInt("Lobby.Y");
		int lobbyZ = this.getConfig().getInt("Lobby.Z");
		
		String sc1World = this.getConfig().getString("SpawnCorner1.World");
		int sc1X = this.getConfig().getInt("SpawnCorner1.X");
		int sc1Y = this.getConfig().getInt("SpawnCorner1.Y");
		int sc1Z = this.getConfig().getInt("SpawnCorner1.Z");
		
		String sc2World = this.getConfig().getString("SpawnCorner2.World");
		int sc2X = this.getConfig().getInt("SpawnCorner2.X");
		int sc2Y = this.getConfig().getInt("SpawnCorner2.Y");
		int sc2Z = this.getConfig().getInt("SpawnCorner2.Z");
		
		int arenaSize = this.getConfig().getInt("ArenaSize");
		int arenaHeight = this.getConfig().getInt("ArenaHeight");
		
		int minPlayers = this.getConfig().getInt("MinPlayers");
		int maxPlayers = this.getConfig().getInt("MaxPlayers");
		
		int waitSecs = this.getConfig().getInt("WaitSecs");
		
		Location lobbyLoc = new Location(this.getServer().getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ);
		Location spawnCorner1 = new Location(this.getServer().getWorld(sc1World), sc1X, sc1Y, sc1Z);
		Location spawnCorner2 = new Location(this.getServer().getWorld(sc2World), sc2X, sc2Y, sc2Z);
		this.arena = new Arena(this, arenaSize, arenaHeight);
		StartProcedure startProc = new StartProcedure(arena);
		StopProcedure stopProc = new StopProcedure(arena);
		this.lobby = new Lobby(this, startProc, stopProc, lobbyLoc, spawnCorner1, spawnCorner2, minPlayers, maxPlayers, waitSecs * 20);
		
		pm.registerEvents(new PlayerListener(this.lobby), this);
		pm.registerEvents(new GameOverListener(this, this.lobby), this);
		
		this.log.info("PillarSpleef 1.0 has been enabled!");
		
		this.arena.resetArena();
	}
	  
	public void loadConfiguration() {
		this.getConfig().options().header("###############################################\nPillarSpleef 1.0\nFor Bukkit Build 1.13.2 R0.1\nBy: Sean Valley\n###############################################\n");
	    
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spleef")) {
			if(args[0].equals("reset")) {
				arena.stopSpleef();
				arena.resetArena();
				lobby.setGameOpen(true);
				((Player)sender).sendMessage(ChatColor.GREEN + "Spleef Reset!");
				return true;
			}else if(args[0].equals("generate")) {
				arena.generateArena();
				((Player)sender).sendMessage(ChatColor.GREEN + "Arena Generated!");
				return true;
			}else if(args[0].equals("setlobby") && sender instanceof Player) {
				Location loc = ((Player)sender).getLocation();
				this.getConfig().set("Lobby.World", loc.getWorld().getName());
				this.getConfig().set("Lobby.X", loc.getBlockX());
				this.getConfig().set("Lobby.Y", loc.getBlockY());
				this.getConfig().set("Lobby.Z", loc.getBlockZ());
				this.saveConfig();
				((Player)sender).sendMessage(ChatColor.GREEN + "Lobby Location Updated!");
				return true;
			}else if(args[0].equals("setspawncorner1") && sender instanceof Player) {
				Location loc = ((Player)sender).getLocation();
				this.getConfig().set("SpawnCorner1.World", loc.getWorld().getName());
				this.getConfig().set("SpawnCorner1.X", loc.getBlockX());
				this.getConfig().set("SpawnCorner1.Y", loc.getBlockY());
				this.getConfig().set("SpawnCorner1.Z", loc.getBlockZ());
				this.saveConfig();
				((Player)sender).sendMessage(ChatColor.GREEN + "SC1 Location Updated!");
				return true;
			}else if(args[0].equals("setspawncorner2") && sender instanceof Player) {
				Location loc = ((Player)sender).getLocation();
				this.getConfig().set("SpawnCorner2.World", loc.getWorld().getName());
				this.getConfig().set("SpawnCorner2.X", loc.getBlockX());
				this.getConfig().set("SpawnCorner2.Y", loc.getBlockY());
				this.getConfig().set("SpawnCorner2.Z", loc.getBlockZ());
				this.saveConfig();
				((Player)sender).sendMessage(ChatColor.GREEN + "SC2 Location Updated!");
				return true;
			}else if(args[0].equals("list") && sender instanceof Player) {
				((Player)sender).sendMessage("In Lobby:");
				for(Player p : this.lobby.getPlayers()) {
					((Player)sender).sendMessage("  " + p.getDisplayName());
				}
				((Player)sender).sendMessage("In Game:");
				for(Player p : this.lobby.getPlayersInGame()) {
					((Player)sender).sendMessage("  " + p.getDisplayName());
				}
				return true;
			}
		}
		return false;
	}
}