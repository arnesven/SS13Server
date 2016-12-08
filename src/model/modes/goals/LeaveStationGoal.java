package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.map.GameMap;

/**
 * Created by erini02 on 08/12/16.
 */
public class LeaveStationGoal extends PersonalGoal {

    private boolean wentOffStation = false;

    @Override
    public String getText() {
        return "Go explore. Leave the station, but be back before the end of the game.";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CheckIfOffStationDecorator(belongingTo.getCharacter()));
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            return wentOffStation && gameData.getMap().getLevelForRoom(getBelongsTo().getPosition()).equals(GameMap.STATION_LEVEL_NAME);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class CheckIfOffStationDecorator extends CharacterDecorator {


        public CheckIfOffStationDecorator(GameCharacter character) {
            super(character, "checkifoffstation");
        }

        @Override
        public void doAfterMovement(GameData gameData) {
            try {
                if (!gameData.getMap().getLevelForRoom(getActor().getPosition()).equals(GameMap.STATION_LEVEL_NAME)) {
                    wentOffStation = true;
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }
}
