package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.general.CloneOMatic;

public class StuffCorpseIntoClonerAction extends TargetingAction {

    private CloneOMatic cloneOMatic;
    private final CloneOMatic cloner;

    public StuffCorpseIntoClonerAction(CloneOMatic cloneOMatic, CloneOMatic cloner, Actor ap) {
        super("Stuff Corpse", SensoryLevel.PHYSICAL_ACTIVITY, ap);
        this.cloneOMatic = cloneOMatic;
        this.cloner = cloner;
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (target instanceof Actor) {
            try {
                target.getPosition().removeActor((Actor)target);
                cloneOMatic.storeActor((Actor) target);
                if (((Actor) target).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter)) {
                    cloner.addCharge(1.0);
                } else {
                    cloner.addCharge(0.5);
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "stuffed " + getTarget().getName() + " into Clone-O-Matic.";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2.isDead();
    }
}
