package model.modes;

import model.GameData;
import model.Player;

public class AssassinateObjective implements TraitorObjective {

	private static final int POINTS_WORTH = 500;
	private Player target;
	private boolean completed = false;
	private Player assassin;

	public AssassinateObjective(Player belongingTo, Player target) {
		this.target = target;
		this.assassin = belongingTo;
	}

	@Override
	public String getText() {
		return "Assassinate the " + target.getBaseName();
	}

	@Override
	public boolean isCompleted(GameData gameData) {
		if (target.isDead() && 
				target.getCharacter().getKillerString().equals(assassin.getBaseName())) {
			completed  = true;
		}
		return completed;
	}

	@Override
	public int getPoints() {
		return POINTS_WORTH;
	}

	@Override
	public boolean wasCompleted() {
		return completed;
	}

}