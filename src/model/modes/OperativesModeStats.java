package model.modes;

import model.Actor;
import model.GameData;

public class OperativesModeStats extends GameStats {

	private OperativesGameMode mode;

	public OperativesModeStats(GameData gameData, OperativesGameMode mode) {
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
		return "Operatives";
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
		if (mode.isNuked()) {
			return "<img src='http://www.ida.liu.se/~erini02/ss13/explosion.gif'></img>";
		} else {
            return "<img width='450' src='http://www.ida.liu.se/~erini02/ss13/disk.png'>";
        }
		//return super.getTopContent();
	}

	@Override
	public String getContent() {
		return "";
	}

}
