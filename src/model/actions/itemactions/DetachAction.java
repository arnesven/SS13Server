package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.npcs.CommandableNPC;
import model.npcs.NPC;
import model.npcs.behaviors.CommandedByBehavior;
import model.objects.DetachableObject;

import java.util.List;

public class DetachAction extends TargetingAction {

    public DetachAction(Actor cl) {
        super("Detach", SensoryLevel.PHYSICAL_ACTIVITY, cl);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        performingClient.addTolastTurnInfo("You started detaching the " + target.getName() + ", this might take a little time...");
        gameData.addEvent(new DetachingEvent(gameData, performingClient, (DetachableObject)target));
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return target2 instanceof DetachableObject;
    }

    @Override
    protected boolean requiresProximityToTarget() {
        return true;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "started to detach";
    }

    private class DetachingEvent extends Event {
        private final int roundSet;
        private final Actor performer;
        private final DetachableObject target;

        public DetachingEvent(GameData gameData, Actor performingClient, DetachableObject target) {
            this.roundSet = gameData.getRound();
            performingClient.setCharacter(new IsDetachingDecorator(performingClient.getCharacter()));
            this.performer = performingClient;
            this.target = target;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() == roundSet + target.getDetatchTimeRounds()) {
                performer.addTolastTurnInfo("You finished detaching the " + target.getName() + ". " +
                        target.getDetachingDescription());
                target.detachYourself(gameData, performer);
                for (Actor a : performer.getPosition().getActors()) {
                    if (a != performer) {
                        a.addTolastTurnInfo(performer.getPublicName(a) + " finished detaching " +
                                target.getName() + ". " + target.getDetachingDescription());
                    }
                }
                performer.removeInstance((GameCharacter gc) -> gc instanceof IsDetachingDecorator);
            }

        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return null;
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() == roundSet + target.getDetatchTimeRounds();
        }
    }

    private class IsDetachingDecorator extends CharacterDecorator {
        public IsDetachingDecorator(GameCharacter character) {
            super(character, "Is detaching");
        }

        @Override
        public boolean getsActions() {
            return false;
        }

        @Override
        public String getFullName() {
            return super.getFullName() + " (Detaching " + target.getName() + ")";
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            super.doAfterMovement(gameData);
            if (getActor() instanceof Player) {
                ((Player) getActor()).setNextAction(new ContinueDetachingAction());
            } else if (getActor() instanceof CommandableNPC) {
                NPC npc = (NPC)getActor();
                ((CommandedByBehavior)npc.getActionBehavior()).setNextAction(new ContinueDetachingAction());
            }
        }
    }

    private class ContinueDetachingAction extends Action {

        public ContinueDetachingAction() {
            super("Continue Detaching", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "detached";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {

        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }

        @Override
        public boolean doesCommitThePlayer() {
            return true;
        }
    }
}
