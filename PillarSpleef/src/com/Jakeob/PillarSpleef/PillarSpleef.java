package com.Jakeob.PillarSpleef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Jakeob.LobbyUtil.Lobby;
import com.Jakeob.LobbyUtil.Spawn;
import com.Jakeob.PillarSpleef.GameProcedures.SpawnPlayerProcedure;
import com.Jakeob.PillarSpleef.GameProcedures.StartProcedure;
import com.Jakeob.PillarSpleef.GameProcedures.StopProcedure;
import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
import com.yapzhenyie.GadgetsMenu.player.PlayerManager;

public class PillarSpleef extends JavaPlugin{
	public Logger log;
	
	private Arena arena;
	private Lobby lobby;
	
	
	/**
	 * Pillar Spleef 1.0
	 * By: Sean Valley
	 * Email: valleydsean@gmail.com
	 */
	public void onDisable() {
		this.log.info("PillarSpleef 1.1 has been disabled!");
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
		this.arena = new Arena(this, arenaSize, arenaHeight);
		
		StartProcedure startProc = new StartProcedure(arena);
		StopProcedure stopProc = new StopProcedure(arena);
		SpawnPlayerProcedure spawnProc = new SpawnPlayerProcedure();
		
		ArrayList<Spawn> spawns = new ArrayList<Spawn>();
		Location spawnCorner1 = new Location(this.getServer().getWorld(sc1World), sc1X, sc1Y, sc1Z);
		Location spawnCorner2 = new Location(this.getServer().getWorld(sc2World), sc2X, sc2Y, sc2Z);
		Spawn spawn = new Spawn(spawnCorner1, spawnCorner2);
		spawns.add(spawn);
		
		this.lobby = new Lobby(this, startProc, stopProc, spawnProc, lobbyLoc, spawns, minPlayers, maxPlayers, waitSecs * 20);
		
		pm.registerEvents(new PlayerListener(this, this.lobby), this);
		pm.registerEvents(new GameOverListener(this, this.lobby), this);
		
		Collection<? extends Player> onlinePlayers = this.getServer().getOnlinePlayers();
		for(Player player : onlinePlayers) {
			lobby.joinPlayer(player);
			player.sendMessage(ChatColor.GREEN + "Joined Lobby!");
		}
		
		this.log.info("PillarSpleef 1.1 has been enabled!");
		
		this.arena.resetArena();
	}
	  
	public void loadConfiguration() {
		this.getConfig().options().header("###############################################\nPillarSpleef 1.1\nFor Bukkit Build 1.13.2 R0.1\nBy: Sean Valley\n###############################################\n");
	    
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spleef") && args[0].equalsIgnoreCase("list") && sender instanceof Player) {
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
		return false;
	}
	
	public boolean isPlayerInConfig(Player player) {
		String id = player.getUniqueId().toString();
		return this.getConfig().contains("Players." + id);
	}
	
	public int getPlayerPoints(Player player) {
		String id = player.getUniqueId().toString();
		return this.getConfig().getInt("Players." + id);
	}
	
	public void setPlayerPoints(Player player, int points) {
		String id = player.getUniqueId().toString();
		this.getConfig().set("Players." + id, points);
		this.saveConfig();
	}
	
	public void incrementPlayerPoints(Player player) {
		int newPoints = getPlayerPoints(player) + 1;
		setPlayerPoints(player, newPoints);
	}
	
	public static void addMysteryDust(Player player, int amount) {
	    PlayerManager playerManager = GadgetsMenuAPI.getPlayerManager(player);
	    playerManager.addMysteryDust(1);
	}
}