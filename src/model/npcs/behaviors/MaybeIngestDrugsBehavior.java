package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.items.chemicals.DrugDose;
import model.items.general.GameItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class MaybeIngestDrugsBehavior implements ActionBehavior {
    @Override
    public void act(Actor npc, GameData gameData) {
        if (MyRandom.nextDouble() < 0.25) {
            List<GameItem> items = new ArrayList<>();
            items.addAll(npc.getItems());
            for (GameItem g : npc.getItems()) {
                if (g instanceof DrugDose) {
                    ((DrugDose) g).beEaten(npc, gameData);
                }
            }
        }
    }
}
