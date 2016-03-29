package model.modes;

import model.GameData;
import model.Player;
import model.items.GameItem;
import model.items.Locatable;

public class LarcenyObjective implements TraitorObjective {

	private GameData gameData;
	private Player victim;
	private GameItem item;
	private Player traitor;

	public LarcenyObjective(GameData gameData, Player traitor, Player victim, GameItem it) {
		this.gameData = gameData;
		this.victim = victim;
		this.traitor = traitor;
		this.item = it;
	}

	@Override
	public String getText() {
		return "Steal the " + victim.getBaseName() + "'s " + item.getBaseName();
	}

	@Override
	public boolean isCompleted(GameData gameData) {
		return GameItem.hasAnItem(traitor, item);
	}

	@Override
	public boolean wasCompleted() {
		return isCompleted(gameData);
	}

	@Override
	public int getPoints() {
		return 600;
	}

	@Override
	public Locatable getLocatable() {
		return GameItem.getItem(victim, item);
	}

}
