package com.Jakeob.LobbyUtil;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Lobby Util 1.0
 * By: Sean Valley
 * Email: valleydsean@gmail.com
 * 
 * A Lobby object with the ability to keep track of players in a lobby and in a game
 * Manages when games start and which players are put into the game
 * 
 * How to use:
 * Initialize a Lobby object with parameters listed in constructor
 * The gameStart and gameEnd GameProcedures' run() methods are called when the game is started and when Lobby.gameComplete() is called
 * Add players to the lobby with Lobby.addPlayer() and remove players from the lobby with Lobby.removePlayer()
 * Remove players from the game with Lobby.removePlayer() when they are out of the game
 */

public class Lobby{
	private JavaPlugin plugin;
	private Queue queue;
	
	private ArrayList<Player> players;
	private ArrayList<Player> playersInGame;
	
	private GameProcedure gameEnd;
	
	private Location lobbyLoc;
	private Location spawnCorner1;
	private Location spawnCorner2;
	
	private int minPlayers;
	private int maxPlayers;
	private int queueWait;
	private boolean gameOpen;
	
	public Lobby(JavaPlugin plugin, GameProcedure gameStart, GameProcedure gameEnd, Location lobbyLoc, Location spawnCorner1, Location spawnCorner2, int minPlayers, int maxPlayers, int queueWait) {
		this.plugin = plugin;
		this.queue = new Queue(this, gameStart);
		
		this.players = new ArrayList<Player>();
		this.playersInGame = new ArrayList<Player>();
		
		this.gameEnd = gameEnd;
		
		this.lobbyLoc = lobbyLoc;
		this.spawnCorner1 = spawnCorner1;
		this.spawnCorner2 = spawnCorner2;
		
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.queueWait = queueWait;
		this.gameOpen = true;	
		
		this.init();
	}
	
	public void gameComplete() {
		this.gameOpen = true;
		this.playersInGame.clear();
		this.gameEnd.run();
	}

	//Adds player to lobby and teleports them to the lobby location
	public void joinPlayer(Player player) {
		players.add(player);
		player.teleport(lobbyLoc);
	}
	
	//Adds player to lobby without teleporting them to the lobby location
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void clearPlayers() {
		players.clear();
	}
	
	public void addPlayerToGame(Player player) {
		playersInGame.add(player);
	}
	
	public void removePlayerFromGame(Player player) {
		playersInGame.remove(player);
	}
	
	public void clearPlayersInGame() {
		playersInGame.clear();
	}
	
	public void setGameOpen(boolean gameOpen) {
		this.gameOpen = gameOpen;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public ArrayList<Player> getPlayersInGame(){
		return this.playersInGame;
	}
	
	public boolean isPlayerInGame(Player player) {
		return this.playersInGame.contains(player);
	}
	
	public boolean isPlayerInLobby(Player player) {
		return this.players.contains(player);
	}
	
	public boolean isGameOpen() {
		return this.gameOpen;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public int getMinPlayers() {
		return this.minPlayers;
	}
	
	public Location getLobbyLoc() {
		return this.lobbyLoc;
	}
	
	public Location getSpawnCorner1() {
		return this.spawnCorner1;
	}
	
	public Location getSpawnCorner2() {
		return this.spawnCorner2;
	}
	
	private void init() {
		//Start protecting players from damage while waiting in lobbies
		this.plugin.getServer().getPluginManager().registerEvents(new PlayerListener(this), this.plugin);
		//Start the queue process
		this.queue.runTaskTimer(plugin, this.queueWait, this.queueWait);
	}
}