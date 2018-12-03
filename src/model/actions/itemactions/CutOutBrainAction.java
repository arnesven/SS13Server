package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.OnSurgeryTableDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.Brain;
import model.items.general.GameItem;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 12/10/16.
 */
public class CutOutBrainAction extends TargetingAction {

    public CutOutBrainAction(Actor ap) {
        super("Cut Out Brain", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        Actor targetAsActor = (Actor)target;
        if (!hasBrainRemoved(targetAsActor)) {
            Brain b = cutOutABrainFrom(performingClient, targetAsActor);
            performingClient.addItem(b, target);
            performingClient.addTolastTurnInfo("You cut out a brain from " + target.getName());
            targetAsActor.addTolastTurnInfo(performingClient.getBaseName() + " cut out your brain!");
        } else {
            performingClient.addTolastTurnInfo(target.getName() + "'s brain is already removed. " + Action.FAILED_STRING);
        }
    }

    public static Brain cutOutABrainFrom(Actor performingClient, Actor targetAsActor) {
        targetAsActor.getCharacter().getPhysicalBody().removeBrain();
        targetAsActor.getCharacter().setHealth(0.0);
        return new Brain(targetAsActor, performingClient);
    }

    @Override
    public List<Target> getTargets() {
        List<Target> targets = new ArrayList<>();
        targets.addAll(super.getTargets());
        for (Target t : super.getTargets()) {
            if (t instanceof Actor) {
                if (!canHaveBrainCutOut((Actor) t)) {
                    targets.remove(t);
                }
            } else {
                targets.remove(t);
            }

        }
        return targets;
    }

    public static boolean canHaveBrainCutOut(Actor t) {
        if (hasBrainRemoved(t)) {
            return false;
        }

        if (!isHuman(t)) {
            return false;
        }

        if (t.isDead()) {
            return true;
        }

        if (((Actor) t).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator)) {
            return true;
        }



        return false;
    }

    private static boolean isHuman(Actor t) {
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

    private static boolean hasBrainRemoved(Actor t) {
        return t.getCharacter().getPhysicalBody().hasABrain() == false;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Surgically removed a brain";
    }
}
