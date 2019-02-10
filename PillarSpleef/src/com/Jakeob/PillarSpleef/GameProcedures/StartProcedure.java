package com.Jakeob.PillarSpleef.GameProcedures;

import com.Jakeob.LobbyUtil.GameProcedure;
import com.Jakeob.PillarSpleef.Arena;

public class StartProcedure implements GameProcedure{

	private Arena arena;
	
	public StartProcedure(Arena arena) {
		this.arena = arena;
	}
	
	@Override
	public void run() {
		arena.resetArena();
		arena.startSpleef();
	}
}