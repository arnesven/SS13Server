package model.modes;

import java.util.List;
import java.util.Map;

import model.GameData;
import model.Player;
import model.characters.InfectedCharacter;
import model.modes.HostGameMode.GameOver;
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
		buf.append("<tr><td><b>Player     </b></td>");
		buf.append(    "<td><b>Status     </b></td>");
		buf.append(    "<td><b> </b></td></tr>");
		for (Map.Entry<String, Player> entry : gameData.getPlayersAsEntrySet()) {
			buf.append("<tr><td>");
			buf.append(entry.getValue().getBaseName() + " (" + entry.getKey() + ")");
			buf.append("</td><td>");
			buf.append(getStatusStringForPlayer(entry.getValue()));
			buf.append("</td><td>");
			buf.append(getInfectedOrKilledBy(entry.getValue()));
			buf.append("</td><td>");
		}
		buf.append("</table>");
		
		return buf.toString();
	}

	private String getInfectedOrKilledBy(Player value) {
		String result = "";
		
		if (value.isInfected() && value != hostMode.getHostPlayer()) {
			InfectedCharacter chara = (InfectedCharacter)value.getCharacter();
			if (chara.getInfector() != null) {
				result += "<i>Infected by " +  chara.getInfector().getBaseName() + "</i>";
			}
		}
		
		if (value.isDead()) {
			if (value.getCharacter().getKiller() != null) {
				if (!result.equals("")) {
					result += ", ";
				}
				result += "<i>Killed by " + value.getCharacter().getKiller().getBaseName() + "</i>";
			}
		}
		
		return result;
	}

	private String getStatusStringForPlayer(Player value) {
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
		buf.append("<tr><td>Hive HP remaining:</td><td>" + hostMode.getHive().getHealth() + "</td></tr>");
		buf.append("<tr><td>Parasites spawned: </td><td>" + hostMode.getAllParasites().size() + "</td></tr>");
		buf.append("<tr><td>Parasites killed: </td><td>" + countDead(hostMode.getAllParasites()) + "</td></tr>");
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

}
