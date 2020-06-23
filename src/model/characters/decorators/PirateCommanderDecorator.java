package model.characters.decorators;

import model.characters.general.GameCharacter;
import model.npcs.NPC;
import model.npcs.PirateNPC;

public class PirateCommanderDecorator extends NPCCommanderDecorator {
    public PirateCommanderDecorator(GameCharacter character) {
        super(character);
    }

    @Override
    protected boolean canCommand(NPC target2) {
        return target2 instanceof PirateNPC;
    }
}
