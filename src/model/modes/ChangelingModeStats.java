package model.modes;

import model.Actor;
import model.GameData;
import model.characters.ChangelingCharacter;
import model.characters.GameCharacter;
import model.characters.HorrorCharacter;
import model.characters.ParasiteCharacter;

class ChangelingModeStats extends GameStats {

	private ChangelingGameMode mode;

	public ChangelingModeStats(GameData gameData, ChangelingGameMode mode) {
		super(gameData, mode);
		this.mode = mode;
	}

	@Override
	protected String modeSpecificExtraInfo(Actor value) {
		return "";
	}


	@Override
	public String getOutcome() {
		if (mode.getGameResult(gameData) == GameOver.ANTAGONISTS_DEAD ||
				mode.getGameResult(gameData) == GameOver.TIME_IS_UP) {
			return "Crew Team Wins";
		} else if (mode.getGameResult(gameData) == GameOver.PROTAGONISTS_DEAD) {
			return "Changeling Wins";
		} else if (mode.getGameResult(gameData) == GameOver.ALL_DEAD) {
			return "Everybody Lost!";
		}
		return "Outcome";
	}

	@Override
	public String getMode() {
		return "Changeling";
	}

	@Override
	protected String getModeSpecificStatus(Actor value) {
		if (value == mode.getChangeling()) {
			return "<span style='background-color: #FF2222'>Changeling</span>";
		}
		return "";
	}
	
	@Override
	protected String getExtraDeadInfo(Actor value) {
		if (value == mode.getChangeling()) {
			return "(Changeling)";
		}
		return "";
	}

	@Override
	public String getEnding() {
		if (mode.getGameResult(gameData) == GameOver.ANTAGONISTS_DEAD) {
			return "Changeling eliminated";
		} else if (mode.getGameResult(gameData) == GameOver.PROTAGONISTS_DEAD) {
			return "Crew Eliminated";
		} else if (mode.getGameResult(gameData) == GameOver.ALL_DEAD) {
			return "Everybody Lost!";
		}
		return "Time limit reached";
	}

	@Override
	public String getContent() {
		String s = "Changeling sucked life from; ";
		ChangelingCharacter chara = (ChangelingCharacter) mode.getChangeling().getCharacter();
		for (GameCharacter chars : chara.getForms()) {
			if (!(chars instanceof ParasiteCharacter || 
					chars instanceof HorrorCharacter)) {
				s += chars.getBaseName() + ", ";
			}
		}
		return s;
	}
}