package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.npcs.AbstractPirateNPC;
import model.npcs.NPC;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 06/09/17.
 */
public class AttackNonPiratesBehavior extends AttackIfPossibleBehavior {

    @Override
    protected List<Target> getTargets(NPC npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());
        Logger.log("Finding targets to exclude in AttackNonPiratesBehavior for " + npc.getName());
//        for (Target t : atk.getTargets()) {
//            if (t instanceof Actor) {
//                Actor targetAsActor = (Actor)t;
//                if (targetAsActor instanceof AbstractPirateNPC) {
//                    Logger.log(" -> Removed a pirate.");
//                    targets.remove(t);
//                }
//            } else {
//                targets.remove(t);
//            }
//        }
        targets.removeIf((Target t) -> (t.getName().contains("Pirate")));
        targets.removeIf((Target t) -> !(t instanceof Actor));

        return targets;
    }

}
