package model.modes;

import model.Actor;
import model.GameData;

public class InfiltrationModeStats extends GameStats {

	private InfiltrationGameMode mode;

	public InfiltrationModeStats(GameData gameData, InfiltrationGameMode mode) {
		super(gameData, mode);
		this.mode = mode;
		
	}

	@Override
	protected String getModeSpecificStatus(Actor value) {
		if (mode.isOperative(value)) {
			return "<span style='background-color: #FF2222'>Operative</span>";
		}
		return "";
	}

	@Override
	protected String getExtraDeadInfo(Actor value) {
		if (mode.isOperative(value)) {
			return "(Operative)";
		}
		return "";
	}

	@Override
	protected String modeSpecificExtraInfo(Actor value) {
		if (mode.isOperative(value)) {
			return "<i>(Decoy: " + mode.getDecoy(value).getBaseName() + ")</i>";
		}
		return "";
	}

	@Override
	public String getMode() {
		return "Infiltration";
	}

	@Override
	public String getOutcome() {
		GameOver ov = mode.getGameResult(gameData);
		if (ov == GameOver.SHIP_NUKED) {
			return "Operative Team Wins!";
		} else if (ov == GameOver.ALL_DEAD) {
			return "Everybody Lost!";
		}
		return "Crew Team Wins!";
	}

	@Override
	public String getEnding() {
		GameOver ov = mode.getGameResult(gameData);
		if (ov == GameOver.SHIP_NUKED) {
			return "SS13 nuked by operatives";
		} else if (ov == GameOver.TIME_IS_UP) {
			return "Time limit reached";
		} else if (ov == GameOver.ANTAGONISTS_DEAD) {
			return "Operatives killed in action";
		} else if (ov == GameOver.ALL_DEAD) {
			return "All players died.";
		}
		throw new IllegalStateException("Tried to get game outcome before game was over!");
	}

	@Override
	protected String getTopContent() {
		if (mode.getGameResult(gameData) == GameOver.SHIP_NUKED) {
			return "<img src='http://stream1.gifsoup.com/view3/1611505/nuclear-explosion-o.gif'></img>";
		}
		return super.getTopContent();
	}

	@Override
	public String getContent() {
		return "";
	}

}
