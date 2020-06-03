package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.AirDuctRoom;

public class CrawlIntoVentilationShaft extends PersonalGoal {
    private boolean completed = false;

    @Override
    public String getText() {
        return "Crawl into the ventilation shafts during this game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new AirDuctsChecker(belongingTo.getCharacter()));
    }

    private class AirDuctsChecker extends CharacterDecorator {
        public AirDuctsChecker(GameCharacter character) {
            super(character, "Air Ducts checker");
        }

        @Override
        public void doAtEndOfTurn(GameData gameData) {
            super.doAtEndOfTurn(gameData);
            if (getActor().getPosition() instanceof AirDuctRoom) {
                completed = true;
            }
        }
    }
}
