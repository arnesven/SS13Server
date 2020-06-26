package model.characters;

import model.Actor;
import model.GameData;
import model.actions.characteractions.FollowCaptainAction;
import model.actions.characteractions.MoveTowardsPirateShipAction;
import model.actions.general.Action;
import model.characters.general.HumanCharacter;
import model.characters.general.PirateCaptainCharacter;
import model.npcs.NPC;

import java.util.ArrayList;

public abstract class PleasureCharacter extends HumanCharacter {

    public PleasureCharacter(String name, int startRoom, double speed) {
        super(name, startRoom, speed);
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (getActor() instanceof NPC) {
            at.add(new MoveTowardsPirateShipAction());

            Actor pc = null;
            for (Actor a : getPosition().getActors()) {
                if (a.getInnermostCharacter() instanceof PirateCaptainCharacter) {
                    pc = a;
                }
            }
            if (pc != null) {
                at.add(new FollowCaptainAction(pc));
            }
        }
    }
}
