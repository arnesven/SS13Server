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

	public SecuritronCharacter(int i) {
		super("SecuriTRON", i, 0.0);
		this.startRoom = i;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("securitron", "aibots.png", 51, getActor());
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
