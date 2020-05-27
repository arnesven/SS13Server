package model.modes;

import model.Actor;
import model.GameData;

public class TraitorModeStats extends ScoredModeStats {

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

	protected TraitorGameMode getTraitorMode() {
		return traitorMode;
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


	protected String getExtraScoringTableRowsHTML() {
		return "";
	}

	@Override
	public String getMode() {
		return traitorMode.getName();
	}


	protected String getTraitorTeamName() {
		return "Traitor Team";
	}


	@Override
	protected String getAntagonistTeamName() {
		return getTraitorTeamName();
	}

	@Override
	protected String modeSpecificTableRows(String style) {
		StringBuffer buf = new StringBuffer();
		buf.append("<tr><td>" + traitorMode.getName() + " Objective(s)</td><td " + style + ">"   + traitorMode.pointsFromObjectives(gameData)      + "</td></tr>");
		buf.append("<tr><td>Crew Survived</td><td " + style +">"         + traitorMode.pointsFromSavedCrew(gameData)        + "</td></tr>");
		buf.append(getExtraScoringTableRowsHTML());

		return buf.toString();
	}
}
