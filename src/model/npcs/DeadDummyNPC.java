package model.npcs;

import model.Actor;
import model.characters.general.GameCharacter;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;

public class DeadDummyNPC extends NPC {
    public DeadDummyNPC(GameCharacter oldChar) {
        super(oldChar, new MeanderingMovement(0.0), new DoNothingBehavior(), oldChar.getPosition());
        this.setHealth(0.0);
    }

    @Override
    public NPC clone() {
        return new DeadDummyNPC(getCharacter());
    }
}
