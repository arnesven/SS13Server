package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.items.chemicals.DrugDose;
import model.items.general.GameItem;
import util.MyRandom;

public class MaybeIngestDrugsBehavior implements ActionBehavior {
    @Override
    public void act(Actor npc, GameData gameData) {
        if (MyRandom.nextDouble() < 0.4) {
            for (GameItem g : npc.getItems()) {
                if (g instanceof DrugDose) {
                    ((DrugDose) g).beEaten(npc, gameData);
                }
            }
        }
    }
}
