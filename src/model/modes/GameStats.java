package model.modes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.InfectedCharacter;
import model.events.ElectricalFire;
import model.events.Event;
import model.events.OngoingEvent;
import model.npcs.CatNPC;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public abstract class GameStats {
	
	protected GameData gameData;
	private GameMode mode;

	public GameStats(GameData gameData, GameMode mode) {
		this.gameData = gameData;
		this.mode = mode;
	}

	/**
	 * This method is called during player-table generation
	 * for all players who are not dead and will show its result
	 * in the status column.
	 * @param value
	 * @return
	 */
	protected abstract String getModeSpecificStatus(Actor value);
	
	/**
	 * This method is called during player-table generation
	 * for all players who are dead. The result will show up 
	 * after the "Dead" string in the status column
	 * @param value
	 * @return
	 */
	protected abstract String getExtraDeadInfo(Actor value);
	
	/**
	 * This method is called during player-table generation
	 * for all players. The result will show up in the column 
	 * to the right of status, before the "killed by"-info.
	 * @param value
	 * @return
	 */
	protected abstract String modeSpecificExtraInfo(Actor value);

	public abstract String getMode();
	public abstract String getOutcome();
	public abstract String getEnding();
	
	@Override
	public String toString() {
		return "<h3>Game is over!</h3><p><i>Hopefully it didn't suck too badly!</i></p>"+
				"<table>" +
				"<tr><td> Mode: </td><td>" + getMode() + "</td></tr>" +
				"<tr><td> Players: </td><td>" + gameData.getPlayersAsList().size() + "</td></tr>" + 
				"<tr><td> Rounds: </td><td>" + gameData.getRound() + "</td></tr>" +
				"<tr><td> Outcome: </td><td>" + getOutcome() + "</td></tr>" +
				"<tr><td> Ending: </td><td>" + getEnding() + "</td></tr>" +
				"</table> <br/>" + 
				generatePlayersTable() + "<br/>" + 
				getContent() +
				getMiscStats();
	}

	private String generatePlayersTable() {
		StringBuffer buf = new StringBuffer("<table>");
		buf.append("<tr><td><b>Crew      </b></td>");
		buf.append(    "<td><b>HP         </b></td>");
		buf.append(    "<td><b>Status     </b></td>");
		buf.append(    "<td><b> </b></td></tr>");
		for (Map.Entry<String, Player> entry : gameData.getPlayersAsEntrySet()) {
			buf.append("<tr><td>");
			buf.append(entry.getValue().getBaseName() + " (" + entry.getKey() + ")");
			buf.append("</td><td>");
			buf.append(entry.getValue().getHealth() + "");
			buf.append("</td><td>");
			buf.append(getStatusStringForPlayer(entry.getValue()));
			buf.append("</td><td>");
			buf.append(getExtraInfoAndKilledBy(entry.getValue()));
			buf.append("</td><td>");
		}
		for (NPC npc : gameData.getNPCs()) {
			if (npc instanceof HumanNPC) {
				buf.append("<tr><td>");
				buf.append(npc.getBaseName());
				buf.append("</td><td>");
				buf.append(npc.getHealth() + "");
				buf.append("</td><td>");
				buf.append(getStatusStringForPlayer(npc));
				buf.append("</td><td>");
				buf.append(getExtraInfoAndKilledBy(npc));
				buf.append("</td><td>");
			}
		}
		
		buf.append("</table>");
		
		return buf.toString();
	}
	
	private String getExtraInfoAndKilledBy(Actor value) {
		String result = "";
		
		result += modeSpecificExtraInfo(value);

		if (value.isDead()) {
			if (!result.equals("")) {
				result += ", ";
			}
			
			if (value.getCharacter().getKillerString().equals(value.getBaseName())) {
				result += "<i>Committed suicide!</i>";
			} else {
				result += "<i>Killed by ";
				result += value.getCharacter().getKillerString() + "</i>";
			}
		}
		
		return result;
	}

	

	private String getStatusStringForPlayer(Actor value) {
		if (value.isDead()) {

			return "<span style='background-color: #AAAAAA'>Dead" + getExtraDeadInfo(value) + "</span>";
		}
		
		String s = getModeSpecificStatus(value);
		if (!s.equals("")) {
			return s;
		}
		
		return "Alive";
	}



	final private String getMiscStats() {
		return "<br/> <table>" +
		"<tr><td><b>Miscellaneous Stats</b></td><td></td></tr>" +
		"<tr><td> Fires put out: </td><td>"       + getFireString(gameData) + "</td></tr>" +
		"<tr><td> Hull breaches fixed: </td><td>" + getHullString(gameData) + "</td></tr>" +	
		"<tr><td> Cat survived: </td><td>"        + isCatDead(gameData) + "</td></tr>" +
		"<tr><td>Parasites spawned: </td><td>"    + mode.getAllParasites().size() + "</td></tr>" +
		"<tr><td>Parasites killed: </td><td>"     + countDead(mode.getAllParasites()) + "</td></tr>" +
		"<tr><td>Parasite vanquisher: </td><td>"  + findVanquisher(mode.getAllParasites())+ "</td></tr>"+
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

	private String getFireString(GameData gameData) {
		OngoingEvent fire = (OngoingEvent) mode.getEvents().get("fires");
		return fire.noOfFixed() + "/" + fire.noOfOngoing();
	}
	
	private String getHullString(GameData gameData2) {
		OngoingEvent hull = (OngoingEvent) mode.getEvents().get("hull breaches");
		return hull.noOfFixed() + "/" + hull.noOfOngoing();
	}
	
	private int countDead(List<NPC> allParasites) {
		int sum = 0;
		for (NPC para : allParasites) {
			if (para.isDead()) {
				sum++;
			}
		}
		return sum;
	}

	
	private String findVanquisher(List<NPC> allParasites) {
		HashMap<String, Integer> map = new HashMap<>();
		for (NPC n : allParasites) {
			if (n.isDead()) {
				String key = n.getCharacter().getKillerString();
				if (map.containsKey(n.getCharacter().getKillerString())) {
					map.put(key, map.get(key) + 1);
				} else {
					map.put(key, 1);
				}
			}
		}
		
		int max = 0;
		String maxName = "Nobody";
		for (Entry<String, Integer> ent : map.entrySet()) {
			if (ent.getValue() > max) {
				maxName = ent.getKey();
			}
		}
		
		return maxName;
	}

	
}
