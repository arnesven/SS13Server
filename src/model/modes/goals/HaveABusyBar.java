package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import util.Logger;

public class HaveABusyBar extends PersonalGoal {
    private boolean completed;
    private final int numPeople;

    public HaveABusyBar(int numPeople) {
        this.completed = false;
        this.numPeople = numPeople;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new BarCountingDecorator(belongingTo.getCharacter()));
    }

    @Override
    public String getText() {
        return "Have at least " + numPeople + " people (humans) in the bar during one round of the game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    private class BarCountingDecorator extends CharacterDecorator {
        public BarCountingDecorator(GameCharacter character) {
            super(character, "Bar Counting");
        }

        @Override
        public void doAtEndOfTurn(GameData gameData) {
            super.doAtEndOfTurn(gameData);

            int humansInBar = 0;
            Room bar = null;
            try {
                bar = gameData.getRoom("Bar");
                for (Actor a : bar.getActors()) {
                    if (a.isHuman()) {
                        humansInBar++;
                    }
                }
            } catch (NoSuchThingException e) {
                Logger.log("Couldn't find bar! Can't check personal goal!");
                e.printStackTrace();
            }

            if (humansInBar >= numPeople) {
                completed = true;
            }
        }
    }
}
