package model.modes.goals;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.modes.GameMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 05/12/16.
 */
public class GuessGameModeGoal extends PersonalGoal {
    private final int round;

    private boolean guessedRight = false;

    public GuessGameModeGoal(int i) {
        round = i;
    }

    @Override
    public String getText() {
        return "Guess the game mode before round " + round + ".";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return guessedRight;
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor potential) {
        return gameData.getSelectedMode().toLowerCase().equals("secret");
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new GuessGameModeDecorator(belongingTo.getCharacter()));
    }

    private class GuessGameModeDecorator extends CharacterDecorator {
        private boolean guessed = false;

        public GuessGameModeDecorator(GameCharacter character) {
            super(character, "guessgamemode");
        }

        @Override
        public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
            super.addCharacterSpecificActions(gameData, at);
            if (gameData.getRound() < round && !guessed) {
                at.add(new GuessGameModeAction());
            }
        }

        private class GuessGameModeAction extends Action {


            private String selected;

            public GuessGameModeAction() {
                super("Guess Game Mode", SensoryLevel.NO_SENSE);
            }

            @Override
            protected String getVerb(Actor whosAsking) {
                return "Guessed the mode";
            }

            @Override
            public ActionOption getOptions(GameData gameData, Actor whosAsking) {
                ActionOption opt =  super.getOptions(gameData, whosAsking);

                for (String str : GameMode.getAvailableModes()) {
                    if (!str.toLowerCase().equals("secret")) {
                        opt.addOption(str);
                    }
                }

                return opt;
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                if (selected.equals(gameData.getGameMode().getName().toLowerCase())) {
                    guessedRight = true;
                    performingClient.addTolastTurnInfo("You guessed it right!"); // 
                } else {
                    performingClient.addTolastTurnInfo("You guessed wrong."); // TODO: write what was guessed
                }
                guessed = true;
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {
                selected = args.get(0).toLowerCase();
            }
        }
    }
}
