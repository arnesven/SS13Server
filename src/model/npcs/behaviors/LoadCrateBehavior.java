package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.characters.decorators.CarryingCrateDecorator;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

public class LoadCrateBehavior implements ActionBehavior {
    private String crateName = null;

    @Override
    public void act(Actor npc, GameData gameData) {
        CrateObject crate = null;
        for (GameObject obj2 : npc.getPosition().getObjects()) {
            if (obj2 instanceof CrateObject) {
                if (crateName == null || crateName.equals(obj2.getPublicName(npc))) {
                    crate = (CrateObject) obj2;
                    break;
                }
            }
        }
        if (crate == null) {
            return;
        }

        npc.getPosition().removeObject(crate);
        if (crate.isOpen()) {
            crate.close();
        }
        npc.setCharacter(new CarryingCrateDecorator(npc.getCharacter(), crate));
    }

    public void setTarget(String crateName) {
        this.crateName = crateName;
    }
}
