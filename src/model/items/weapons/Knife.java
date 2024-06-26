package model.items.weapons;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.actions.itemactions.CutOutBrainAction;
import model.actions.itemactions.SurgeryAction;

import java.util.ArrayList;

public class Knife extends SlashingWeapon {

	public Knife() {
        super("Knife", 0.75, 1.0, false, 0.5, true, 60);

	}

	@Override
	public Knife clone() {
		return new Knife();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("knife", "kitchen.png", 6, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        TargetingAction cut = new CutOutBrainAction(cl);
        if (cut.getTargets().size() > 0) {
            at.add(cut);
        }

        SurgeryAction sur = new SurgeryAction();
        if (sur.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(sur);
        }
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good for cutting stuff up, like food and/or people. Needed to perform surgery.";
    }
}
