package model.npcs.behaviors;

import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.npcs.NPC;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class AttackAllIfPossibleBehavior extends AttackIfPossibleBehavior {
    @Override
    protected List<Target> getTargets(NPC npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());

        for (Target t : atk.getTargets()) {
            if (t instanceof NPC) {
                NPC targetAsNPC = (NPC)t;
                if (targetAsNPC.getClass() == npc.getClass()) {
                    targets.remove(t);
                }
            }
        }
        return targets;
    }
}
