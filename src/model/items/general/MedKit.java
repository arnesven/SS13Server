package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.HealWithMedKitAction;
import model.actions.general.TargetingAction;


public class MedKit extends GameItem {

	
	public MedKit() {
		super("MedKit", 1.0, true);
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {	
		TargetingAction act = new HealWithMedKitAction(cl, this);
		if (act.getTargets().size() > 0) {
			at.add(act);
		}
	}

	@Override
	public MedKit clone() {
		return new MedKit();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("medkit", "storage.png", 4);
    }
}
