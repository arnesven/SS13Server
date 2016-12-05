package model.modes.goals;

import model.Actor;
import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.Room;
import model.map.RoomType;

/**
 * Created by erini02 on 05/12/16.
 */
public class GoIntoSpaceGoal extends PersonalGoal {
    private boolean completed = false;

    @Override
    public String getText() {
        return "Go take a walk in space";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return completed;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new CheckSpaceWalkDecorator(belongingTo.getCharacter()));
    }

    private class CheckSpaceWalkDecorator extends CharacterDecorator {
        public CheckSpaceWalkDecorator(GameCharacter character) {
            super(character, "checkspacewalk");
        }


        @Override
        public void setPosition(Room room) {
            super.setPosition(room);
            if (room.getType() == RoomType.space) {
                completed = true;
            }
        }
    }
}
