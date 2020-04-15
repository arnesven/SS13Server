package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.HealWithMedKitAction;
import model.actions.general.TargetingAction;
import model.items.HandheldItem;


public class MedKit extends GameItem implements HandheldItem {

	private int uses = 2;
	private int max_uses = 4;

	public MedKit() {
		super("MedKit", 1.0, true, 50);
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
		TargetingAction act = new HealWithMedKitAction(cl, this);
		if (act.getTargets().size() > 0) {
			at.add(act);
		}
	}

	public void decrementUses() {
		this.uses--;
	}

	public boolean isEmpty() {
		return uses == 0;
	}

	@Override
	public MedKit clone() {
		return new MedKit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("medkit", "storage.png", 4, this);
    }

	@Override
	public Sprite getHandHeldSprite() {
		return new Sprite("medkithandheld", "items_righthand.png", 0, 17, this);
	}
}
