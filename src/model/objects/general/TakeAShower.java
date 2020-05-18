package model.objects.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;

import java.util.List;

    public class TakeAShower extends Action {
        public TakeAShower() {
            super("Take a Shower", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "showered";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
                performingClient.addTolastTurnInfo("You were put out by the shower.");
            } else {
                performingClient.addTolastTurnInfo("You don't really feel much cleaner...");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

