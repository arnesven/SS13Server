package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.npcs.NPC;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 14/04/16.
 */
public class HealOtherBehavior implements ActionBehavior {
    @Override
    public void act(NPC npc, GameData gameData) {
        List<Actor> healbles = new ArrayList<>();
       for (Actor a : npc.getPosition().getActors()) {
            if (a.getCharacter().isHealable()) {
                healbles.add(a);
            }
        }

        if (healbles.size() > 0) {
            Actor target = MyRandom.sample(healbles);
            if (target.getMaxHealth() > target.getCharacter().getHealth()) {
                target.addToHealth(0.5);
                target.addTolastTurnInfo(npc.getPublicName() + " healed you!");
                for (Actor a : npc.getPosition().getActors()) {
                    if (a != target) {
                        a.addTolastTurnInfo(npc.getPublicName() + " healed " + target.getPublicName() + ".");
                    }
                }
            }
        }
    }
}