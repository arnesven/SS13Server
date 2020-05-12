package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.characters.decorators.CarryingCrateDecorator;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.objects.general.CrateObject;

public class UnloadCrateBehavior implements ActionBehavior {
    @Override
    public void act(Actor npc, GameData gameData) {
        if (npc.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CarryingCrateDecorator)) {
            GameCharacter gc = npc.getCharacter();
            CrateObject crate = null;
            while (gc instanceof CharacterDecorator) {
                if (gc instanceof CarryingCrateDecorator) {
                    crate = ((CarryingCrateDecorator)gc).getCrate();
                    break;
                }
                gc = ((CharacterDecorator) gc).getInner();
            }

            npc.getPosition().addObject(crate);
            crate.open();
            npc.removeInstance((GameCharacter gc2) -> gc2 instanceof CarryingCrateDecorator);
        }
    }
}
