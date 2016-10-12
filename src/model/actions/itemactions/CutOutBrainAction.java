package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.BrainRemovedDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.Brain;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 12/10/16.
 */
public class CutOutBrainAction extends TargetingAction {

    public CutOutBrainAction(Actor ap) {
        super("Cut out brain", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        Actor targetAsActor = (Actor)target;
        targetAsActor.setCharacter(new BrainRemovedDecorator(targetAsActor.getCharacter()));
        performingClient.addItem(new Brain(targetAsActor, performingClient), target);
        performingClient.addTolastTurnInfo("You cut out a brain from " + target.getName());
        targetAsActor.addTolastTurnInfo(performingClient.getBaseName() + " cut out your brain!");
    }

    @Override
    public List<Target> getTargets() {
        List<Target> targets = new ArrayList<>();
        targets.addAll(super.getTargets());
        for (Target t : super.getTargets()) {
            if (t instanceof Actor) {
               if ( (! ((Actor)t).isDead()) || hasBrainRemoved((Actor)t) || !isHuman((Actor)t)) {
                    targets.remove(t);
               }
            } else {
                targets.remove(t);
            }
        }
        return targets;
    }

    private boolean isHuman(Actor t) {
        return t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof HumanCharacter;
            }
        });
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return true;
    }

    private boolean hasBrainRemoved(Actor t) {
        return t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof BrainRemovedDecorator;
            }
        });
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Surgically removed a brain";
    }
}
