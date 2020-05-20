package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.WatchAction;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.Tools;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by erini02 on 21/11/16.
 */
public class HandCuffedDecorator extends CharacterDecorator implements DisablingDecorator {
    private static final double FREE_CHANCE = 0.25;

    public HandCuffedDecorator(GameCharacter character) {
        super(character, "handcuffed");
    }

    @Override
    public String getPublicName() {
        return super.getPublicName() + " (handcuffed)";
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        at.removeIf(((Action a) -> !(a instanceof WatchAction) && !(a instanceof MoveAction)));
        at.add(new ResistHandcuffsAction());
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor anyActorInRoom,
                                          ArrayList<Action> at) {
        super.addActionsForActorsInRoom(gameData, anyActorInRoom, at);
        if (anyActorInRoom != this.getActor()) {
            if (GameItem.hasAnItem(anyActorInRoom, new Tools())) {
                at.add(new FreeHandcuffsFrom(getActor()));
            }
        }

    }

    private class ResistHandcuffsAction extends Action {

        public ResistHandcuffsAction() {
            super("Break Loose", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "struggled in handcuffs";
        }


        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (MyRandom.nextDouble() < FREE_CHANCE) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof HandCuffedDecorator);
                performingClient.addTolastTurnInfo("You broke free of the handcuffs!");
            } else {
                performingClient.addTolastTurnInfo("You're almost free...");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class FreeHandcuffsFrom extends Action {
        private final Actor target;

        public FreeHandcuffsFrom(Actor actor) {
            super("Free " + actor.getPublicName(), SensoryLevel.PHYSICAL_ACTIVITY);
            target = actor;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "freed " + target.getPublicName();
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (target.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof HandCuffedDecorator)) {
                target.removeInstance((GameCharacter gc) -> gc instanceof HandCuffedDecorator);
                performingClient.addTolastTurnInfo("You freed " + target.getPublicName() + " from handcuffs.");
                target.addTolastTurnInfo(performingClient.getPublicName() + " freed you from the handcuffs.");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
