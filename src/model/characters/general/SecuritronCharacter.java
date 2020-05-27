package model.characters.general;


import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.TeleBrigAction;
import model.actions.general.Action;
import model.actions.general.TargetingAction;

import java.util.ArrayList;

public class SecuritronCharacter extends RobotCharacter {

	private int startRoom;

    private static int uid = 1;

    public SecuritronCharacter(int i) {
		super("SecuriTRON" + (uid>1?(" #"+uid):""), i, 0.0);
		this.startRoom = i;
		uid++;
	}

    @Override
    protected Sprite getNormalSprite(Actor whosAsking) {
        return new Sprite("securitron", "aibots.png", 51, getActor());
    }

    @Override
    protected Sprite getBrokenSprite(Actor whosAsking) {
        return new Sprite("brokensecuritron", "aibots.png", 60, getActor());
    }

    @Override
	public GameCharacter clone() {
		return new SecuritronCharacter(startRoom);
	}

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        TargetingAction tele = new TeleBrigAction(getActor());
        if (tele.getNoOfTargets() > 0) {
            at.add(tele);
        }
    }
}
