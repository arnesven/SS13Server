package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.AttackAction;
import model.characters.decorators.InstanceChecker;
import model.characters.general.*;
import model.npcs.NPC;
import model.objects.general.GameObject;

import java.util.*;

/**
 * Created by erini02 on 18/04/16.
 */
public class AttackBaddiesBehavior extends AttackIfPossibleBehavior {

    private Set<Actor> extraBaddies = new HashSet<>();

    @Override
    protected List<Target> getTargets(Actor npc, GameData gameData, AttackAction atk) {
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
        if (t instanceof Player) {
            if (((Player) t).getNextAction() instanceof AttackAction) {
                Target victim = (((AttackAction) ((Player) t).getNextAction()).getTarget() );
                if (victim instanceof Actor) {
                    if (((Actor) victim).getCharacter().isCrew()) {
                        extraBaddies.add(t);
                    }
                }
            }
        }

        if (extraBaddies.contains(t)) {
            return true;
        }

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
        }) || t.getCharacter().checkInstance((new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof PirateCharacter;
            }
        }));
    }


}
