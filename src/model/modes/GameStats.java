package model.modes;

import model.GameData;
import model.npcs.CatNPC;
import model.npcs.NPC;

public abstract class GameStats {
	
	protected GameData gameData;

	public GameStats(GameData gameData) {
		this.gameData = gameData;
	}
	
	@Override
	public String toString() {
		return "<h3>Game is over!</h3><p><i>Hopefully it didn't suck too badly!</i></p>"+
				"<table>" +
				"<tr><td> Mode: </td><td>" + getMode() + "</td></tr>" +
				"<tr><td> Players: </td><td>" + gameData.getPlayersAsList().size() + "</td></tr>" + 
				"<tr><td> Rounds: </td><td>" + gameData.getRound() + "</td></tr>" +
				"<tr><td> Outcome: </td><td>" + getOutcome() + "</td></tr>" +
				"<tr><td> Ending: </td><td>" + getEnding() + "</td></tr>" +
				"</table>" + 
				getContent() +
				getMiscStats();
	}

	final private String getMiscStats() {
		return "<br/> <table>" +
		"<tr><td><b>Miscellaneous Stats</b></td><td></td></tr>" +
		"<tr><td> Cat survived: </td><td>" + isCatDead(gameData) + "</td></tr>" +
				"</table>";
	}

	public abstract String getContent();

	private String isCatDead(GameData gameData2) {
		for (NPC npc : gameData2.getNPCs()) {
			if (npc instanceof CatNPC) {
				if (npc.isDead()) {
					return "No";
				} else {
					return "Yes";
				}
			}
		}
		return "No";
	}

	public abstract String getMode();
	public abstract String getOutcome();
	public abstract String getEnding();
	
}
