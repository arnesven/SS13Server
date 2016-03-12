package model.modes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.GameCharacter;
import model.characters.decorators.InfectedCharacter;
import model.modes.HostGameMode.GameOver;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public class HostModeStats extends GameStats {

	
	private HostGameMode hostMode;

	public HostModeStats(GameData gameData, HostGameMode hostMode) {
		super(gameData);
		this.hostMode = hostMode;
	}
	
	@Override
	public String getContent() {
		String content = generatePlayersTable();
		
		content += "<br/>" + generateMiscStatstable();
		
		return "<br/>" + content;
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
			buf.append(getInfectedOrKilledBy(entry.getValue()));
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
				buf.append(getInfectedOrKilledBy(npc));
				buf.append("</td><td>");
			}
		}
		
		buf.append("</table>");
		
		return buf.toString();
	}

	private String getInfectedOrKilledBy(Actor value) {
		String result = "";
		
		if (value.isInfected() && value != hostMode.getHostPlayer()) {
			InfectedCharacter chara = (InfectedCharacter)value.getCharacter();
			if (chara.getInfector() != null) {
				result += "<i>Infected by " +  chara.getInfector().getBaseName() + " in round " + chara.getInfectedInRound() + "</i>";
			}
		}
		
		if (value.isDead()) {
			//if (value.getCharacter().getKillerString()) {
				if (!result.equals("")) {
					result += ", ";
				}
				result += "<i>Killed by " + value.getCharacter().getKillerString() + "</i>";
		//	}
		}
		
		return result;
	}

	private String getStatusStringForPlayer(Actor value) {
		if (value.isDead()) {
			String host = "";
			if (value.isInfected() && value == hostMode.getHostPlayer()) {
				host = " (Host)";
			}
			
			return "<span style='background-color: #AAAAAA'>Dead" + host + "</span>";
		}
		if (value.isInfected()) {
			String val = "Infected";
			if (value == hostMode.getHostPlayer()) {
				val = "Host";
			}
			
			return "<span style='background-color: #FF2222'>" + val + "</span>";
		}
		return "Alive";
	}

	private String generateMiscStatstable() {
		StringBuffer buf = new StringBuffer("<table>");
	
		buf.append("<tr><td>Hive Location:</td><td>" + hostMode.getHiveRoom().getName() + "</td></tr>");
		if (hostMode.getHive().getHealth() > 0) {
			buf.append("<tr><td>Hive HP remaining:</td><td>" + hostMode.getHive().getHealth() + "</td></tr>");
		} else {
			buf.append("<tr><td>Hive destroyed by:</td><td>" + hostMode.getHive().getBreakString() + "</td></tr>");
			
		}
		buf.append("<tr><td>Parasites spawned: </td><td>" + hostMode.getAllParasites().size() + "</td></tr>");
		buf.append("<tr><td>Parasites killed: </td><td>" + countDead(hostMode.getAllParasites()) + "</td></tr>");
		buf.append("<tr><td>Parasite vanquisher: </td><td>" + findVanquisher(hostMode.getAllParasites())+ "</td></tr>");
		buf.append("</table>");
		
		return buf.toString();
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

	@Override
	public String getMode() {
		return "Host";
	}

	@Override
	public String getOutcome() {
		HostGameMode.GameOver status = hostMode.getGameResultType(gameData);
		if (status == GameOver.ALL_INFECTED || status == GameOver.TIME_IS_UP) {
			return "Host Team Wins!";
		}
		if (status == GameOver.HIVE_BROKEN) {
			return "Crew Team Wins!";
		}
		if (status == GameOver.ALL_DEAD) {
			return "Everybody Lost!";
		}
		
		throw new IllegalStateException("Tried to get game outcome before game was over!");
	}

	@Override
	public String getEnding() {
		HostGameMode.GameOver status = hostMode.getGameResultType(gameData);
		if (status == GameOver.ALL_INFECTED) {
			return "All reminaing players infected";
		}
		if (status == GameOver.TIME_IS_UP) {
			return "Time limit reached";
		}
		if (status == GameOver.HIVE_BROKEN) {
			return "Hive destroyed";
		}
		if (status == GameOver.ALL_DEAD) {
			return "All players died";
		}
		
		
		throw new IllegalStateException("Tried to get game outcome before game was over!");
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
