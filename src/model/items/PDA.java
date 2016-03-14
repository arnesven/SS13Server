package model.items;

import java.util.ArrayList;

import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.itemactions.UsePDAAction;
import model.modes.TraitorGameMode;
import model.items.weapons.*;

public class PDA extends GameItem {

	private TraitorGameMode traitorMode;
	private int usesLeft = 2;

	public PDA(TraitorGameMode traitorMode) {
		super("PDA", 0.5);
		this.traitorMode = traitorMode;
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		at.add(new UsePDAAction(traitorMode, this));
	}

	public int getUsesLeft() {
		return usesLeft;
	}

	public void decrementUses() {
		usesLeft = Math.max(usesLeft-1, 0);
	}

	public static List<GameItem> getOrderableItems() {
		List<GameItem> items = new ArrayList<>();
		items.add(new LaserPistol());
		items.add(new Grenade());
		items.add(new Revolver());
		items.add(new StunBaton());
		items.add(new DummyHivePlacer());
		items.add(new TimeBomb());
		items.add(new RemoteBomb());
		return items;
	}
}
