package model.modes;

import model.Actor;
import model.GameData;

public class TraitorModeStats extends GameStats {

	private TraitorGameMode traitorMode;

	public TraitorModeStats(GameData gameData, TraitorGameMode traitorMode) {
		super(gameData, traitorMode);
		this.gameData = gameData;
		this.traitorMode = traitorMode;
	}

	@Override
	protected String getModeSpecificStatus(Actor value) {
		if (traitorMode.getTraitors().contains(value)) {
			return "<span style='background-color: #FF2222'>Traitor</span>";
		}
		return "";
	}

	@Override
	protected String getExtraDeadInfo(Actor value) {
		String traitor = "";
		if (traitorMode.getTraitors().contains(value)) {
			traitor = " (Traitor)";
		}
		return traitor;
	}

	@Override
	protected String modeSpecificExtraInfo(Actor value) {
		if (traitorMode.getTraitors().contains(value)) {
			return "<i>(Objective: " + traitorMode.getObjectives().get(value).getText() + " - " + getSuccessOrFail(value) + ")</i>";
		}
		return "";
	}

	private String getSuccessOrFail(Actor value) {
		if (traitorMode.getObjectives().get(value).wasCompleted()) {
			return "<span style='color: #11DD11'>*Success*</span>";
		}
		return "<span style='color: #DD1111'>*Failed*</span>";
	}

	@Override
	public String getContent() {
		int score =  traitorMode.getScore(gameData);
		String color = "#000000";
		if (score < 0) {
			color ="#DD1111";
		}
		
		String style = "style='text-align:right'";
		
		StringBuffer buf = new StringBuffer("<table>");
		buf.append("<tr><td><b>Total Score</b></td><td style='text-align:right;color="+color+"'><b>" + score + "</b></td></tr>");
		buf.append("<tr><td>Traitor Objectives</td><td " + style + ">" + traitorMode.pointsFromObjectives(gameData) + "</td></tr>");
		buf.append("<tr><td>Crew Surviving</td><td " + style +">"     + traitorMode.pointsFromSavedCrew(gameData)      + "</td></tr>");
		buf.append("<tr><td>Fires</td><td " + style +">"              + traitorMode.pointsFromFires(gameData)          + "</td></tr>");
		buf.append("<tr><td>Hull Breaches</td><td " + style +">"      + traitorMode.pointsFromBreaches(gameData)       + "</td></tr>");		
		buf.append("<tr><td>Parasites killed</td><td " + style +">"   + traitorMode.pointsFromParasites(gameData)       + "</td></tr>");		
		buf.append("<tr><td>Cat </td><td " + style +">"               + traitorMode.pointsFromCat(gameData)       + "</td></tr>");		
		buf.append("<tr><td>TARS </td><td " + style +">"              + traitorMode.pointsFromTARS(gameData)       + "</td></tr>");		
		
		buf.append("</table>");
		return buf.toString();
	}

	@Override
	public String getMode() {
		return "Traitor";
	}

	@Override
	public String getOutcome() {
		GameOver status = traitorMode.getGameResult(gameData);
		if (status == GameOver.TIME_IS_UP) {
			int score = traitorMode.getScore(gameData);
			if (score == 0) {
				return "It's a draw!";
			} else if (score > 0) {
				return "Crew Team Wins!";
			} else {
				return "Traitor Team Wins!";
			}
		}
		if (status == GameOver.PROTAGONISTS_DEAD) {
			return "Traitor Team Wins!";
		}
		if (status == GameOver.ALL_DEAD) {
			return "Everybody Lost!";
		}
		
		throw new IllegalStateException("Tried to get game outcome before game was over!");
	
	}

	@Override
	public String getEnding() {
		GameOver status = traitorMode.getGameResult(gameData);
		if (status == GameOver.TIME_IS_UP) {
			return "Time limit reached";
		}
		if (status == GameOver.PROTAGONISTS_DEAD) {
			return "Crew team eliminated";
		} 
		if (status == GameOver.ALL_DEAD) {
			return "All players died";
		}
		
		throw new IllegalStateException("Tried to get game outcome before game was over!");
	}



}
