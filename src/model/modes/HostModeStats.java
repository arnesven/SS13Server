package model.modes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.InfectedCharacter;
import model.npcs.HumanNPC;
import model.npcs.NPC;

public class HostModeStats extends GameStats {

	
	private HostGameMode hostMode;

	public HostModeStats(GameData gameData, HostGameMode hostMode) {
		super(gameData, hostMode);
		this.hostMode = hostMode;
	}
	
	@Override
	public String getContent() {
		String content = "";
		
		content += "<br/>" + generateMiscStatstable();
		
		return "<br/>" + content;
	}

	@Override
	protected String getModeSpecificStatus(Actor value) {
		if (value.isInfected()) {
			String val = "Infected";
			if (value == hostMode.getHostPlayer()) {
				val = "Host";
			}

			return "<span style='background-color: #FF2222'>" + val + "</span>";
		}
		return "";
	}
	
	@Override
	protected String modeSpecificExtraInfo(Actor value) {
		if (value.isInfected() && value != hostMode.getHostPlayer()) {
			InfectedCharacter chara = (InfectedCharacter)value.getCharacter();
			if (chara.getInfector() != null) {
				return "<i>Infected by " +  chara.getInfector().getBaseName() + " in round " + chara.getInfectedInRound() + "</i>";
			}
		}
		return "";
	}
	
	@Override
	protected String getExtraDeadInfo(Actor value) { 
		String host = "";
		if (value.isInfected() && value == hostMode.getHostPlayer()) {
			host = " (Host)";
		}
		return host;
	}


	private String generateMiscStatstable() {
		StringBuffer buf = new StringBuffer("<table>");
	
		buf.append("<tr><td>Hive Location:</td><td>" + hostMode.getHiveRoom().getName() + "</td></tr>");
		if (hostMode.getHive().getHealth() > 0) {
			buf.append("<tr><td>Hive HP remaining:</td><td>" + hostMode.getHive().getHealth() + "</td></tr>");
		} else {
			buf.append("<tr><td>Hive destroyed by:</td><td>" + hostMode.getHive().getBreakString() + "</td></tr>");
			
		}
		buf.append("</table>");
		
		return buf.toString();
	}

	
	@Override
	public String getMode() {
		return "Host";
	}
	
	


	@Override
	public String getOutcome() {
		GameOver status = hostMode.getGameResultType(gameData);
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
		GameOver status = hostMode.getGameResultType(gameData);
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
