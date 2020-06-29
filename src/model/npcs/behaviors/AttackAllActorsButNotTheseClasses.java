package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.characters.general.GameCharacter;
import model.items.general.CleanUpBloodAction;
import model.npcs.NPC;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class AttackAllActorsButNotTheseClasses extends AttackIfPossibleBehavior {

    private final List<Class<? extends GameCharacter>> classes;

    public AttackAllActorsButNotTheseClasses(List<Class<? extends GameCharacter>> classes) {
        this.classes = classes;
    }

    @Override
    protected List<Target> getTargets(Actor npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());

        for (Target t : atk.getTargets()) {
            if (t instanceof Actor) {
                Actor targetAsActor = (Actor)t;
                if (shouldAvoidTarget(targetAsActor)) {
                    targets.remove(t);
                }



            } else {
                targets.remove(t);
            }
        }
        return targets;
    }

    protected boolean shouldAvoidTarget(Actor targetAsActor) {
        for (Class<? extends GameCharacter> cls : classes) {
            if (targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> cls.isAssignableFrom(gc.getClass()))) {
                return true;
            }
        }
        return false;
    }
}
