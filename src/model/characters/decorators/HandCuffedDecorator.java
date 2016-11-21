package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.WatchAction;
import model.characters.general.GameCharacter;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by erini02 on 21/11/16.
 */
public class HandCuffedDecorator extends CharacterDecorator {
    public HandCuffedDecorator(GameCharacter character) {
        super(character, "handcuffed");
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        at.removeIf(((Action a) -> ! (a instanceof WatchAction)));
        at.add(new ResistHandcuffsAction());
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
            if (MyRandom.nextDouble() < 0.33) {
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
}
