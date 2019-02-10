package com.Jakeob.PillarSpleef.GameProcedures;

import com.Jakeob.LobbyUtil.GameProcedure;
import com.Jakeob.PillarSpleef.Arena;

public class StopProcedure implements GameProcedure{

	private Arena arena;
	
	public StopProcedure(Arena arena) {
		this.arena = arena;
	}
	
	@Override
	public void run() {
		arena.stopSpleef();
	}
}