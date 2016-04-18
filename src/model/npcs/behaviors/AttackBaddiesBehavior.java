package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.general.HorrorCharacter;
import model.characters.general.OperativeCharacter;
import model.characters.general.ParasiteCharacter;
import model.npcs.NPC;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/04/16.
 */
public class AttackBaddiesBehavior extends AttackIfPossibleBehavior {

    @Override
    protected List<Target> getTargets(NPC npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());

        for (Target t : atk.getTargets()) {
            if (t instanceof Actor) {
                if (! isBaddie((Actor)t)) {
                    targets.remove(t);
                }
            } else {
                targets.remove(t);
            }
        }
        return targets;
    }

    private boolean isBaddie(Actor t) {
        return t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof ParasiteCharacter;
            }
        }) || t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof OperativeCharacter;
            }
        }) || t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof HorrorCharacter;
            }
        });
    }

}
