package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.items.foods.Alcohol;
import model.items.general.GameItem;
import model.npcs.NPC;
import util.MyRandom;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateBehavior implements ActionBehavior {
    private AttackAllIfPossibleBehavior attackBehave = new AttackAllIfPossibleBehavior();
    private int thirst = 1;

    @Override
    public void act(NPC npc, GameData gameData) {
        if (hasRum(npc) != null && MyRandom.nextDouble() < thirst*0.1) {
            Alcohol al = hasRum(npc);
            al.beEaten(npc, gameData);
            thirst = 0;
            for (Actor a : npc.getPosition().getActors()) {
                a.addTolastTurnInfo(npc.getPublicName() + " drank some " + al.getBaseName());
            }
        } else {
            attackBehave.act(npc, gameData);
            thirst++;
        }
    }

    private Alcohol hasRum(NPC npc) {
        for (GameItem it : npc.getItems()) {
            if (it instanceof Alcohol) {
                return (Alcohol)it;
            }
        }
        return null;
    }
}