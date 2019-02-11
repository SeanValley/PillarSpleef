package com.Jakeob.LobbyUtil;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public interface SpawnProcedure {
	public void run(ArrayList<Player> players);
	public void setLobby(Lobby lobby);
}