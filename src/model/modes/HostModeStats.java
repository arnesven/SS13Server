package model.modes;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InfectedCharacter;

public class HostModeStats extends GameStats {

	
	private HostGameMode hostMode;

	public HostModeStats(GameData gameData, HostGameMode hostMode) {
		super(gameData, hostMode);
		this.hostMode = hostMode;
	}
	
	@Override
	public String getContent() {
		String content = "";
		
		content += generateMiscStatstable();
		
		return content;
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
			InfectedCharacter chara = getInfectCharacter(value.getCharacter());
			if (chara.getInfector() != null) {
				return "<i>Infected by " +  chara.getInfector().getBaseName() + " in round " + chara.getInfectedInRound() + "</i>";
			}
		}
		return "";
	}
	
	public static InfectedCharacter getInfectCharacter(GameCharacter character) {
		if (character instanceof InfectedCharacter) {
			return (InfectedCharacter)character;
		} else if (character instanceof CharacterDecorator) {
			return getInfectCharacter( ((CharacterDecorator)character).getInner());
		}
		
		throw new IllegalStateException("Tried to find inner char where none existed");
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
        if (hostMode.getHive().isFound()) {
            buf.append("<tr><td>Hive Discovered By:</td><td>" + hostMode.getHive().getFinder().getBaseName() + "</td></tr>");
        } else {
            buf.append("<tr><td>Hive remained hidden!</td><td></td></tr>");
        }

		if (hostMode.getHive().getHealth() > 0) {
			buf.append("<tr><td>Hive HP remaining:</td><td>" + hostMode.getHive().getHealth() + "</td></tr>");
		} else {
			buf.append("<tr><td>Hive destroyed by:</td><td>" + hostMode.getHive().getBreakString() + "</td></tr>");
		}
		buf.append("</table>");

        buf.append("Hive Damage History:<p>");
        for (String s : hostMode.getHive().getDamageHistory()) {
            buf.append(s + "<br/>");
        }
        buf.append("</p>");
		
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
			return "All remaining players infected";
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
