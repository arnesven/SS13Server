package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.FaceHuggedDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.GameItem;

/**
 * Created by erini02 on 30/11/16.
 */
public class FaceHuggingAction extends TargetingAction {
    public FaceHuggingAction(Actor ap) {
        super("Facehug (kills you)", SensoryLevel.PHYSICAL_ACTIVITY, ap);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        performingClient.getCharacter().setHealth(0);
        performingClient.addTolastTurnInfo("You facehugged and layed eggs inside " + target.getName());
        ((Actor)target).addTolastTurnInfo(performingClient.getPublicName() + " facehugged you and laid eggs inside of you - ugh!");
        ((Actor)target).setCharacter(new FaceHuggedDecorator(((Actor)target).getCharacter(), gameData));
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof Actor &&
                (((Actor) target2).getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HumanCharacter));
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "facehugged";
    }
}
