package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.HealWithMedKitAction;
import model.actions.general.TargetingAction;
import model.items.HandheldItem;


public class MedKit extends GameItem implements HandheldItem {

	private int uses = 2;
	private int max_uses = 2;

	public MedKit() {
		super("MedKit", 1.0, true, 50);
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
		super.addYourActions(gameData, at, cl);
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

	@Override
	public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
		return super.getExtraDescriptionStats(gameData, performingClient) + "<b>Uses:</b> " + uses + "/" + max_uses + "<br/>";
	}

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return "Good for healing people, although it can only be used a couple of times before the supplies run out.";
	}
}
