package model.modes.goals;

import model.Actor;
import model.GameData;
import model.events.BackgroundEvent;
import model.map.rooms.BrigRoom;
import model.map.GameMap;
import model.map.rooms.Room;

/**
 * Created by erini02 on 08/12/16.
 */
public class BrigAnAntagonistGoal extends PersonalGoal {
    private boolean antagonistWasInGrig = false;
    private boolean failed = false;

    public BrigAnAntagonistGoal(GameData gameData) {
        gameData.addEvent(new CheckIfAntagonistIsInBrigEvent());
    }

    @Override
    public String getText() {
        return "Put an antagonist in the brig. Do NOT put innocents in the brig.";
    }


    @Override
    public boolean isCompleted(GameData gameData) {
        return antagonistWasInGrig && !failed;
    }

    private class CheckIfAntagonistIsInBrigEvent extends BackgroundEvent {
        @Override
        public void apply(GameData gameData) {
            for (Room r : gameData.getMap().getRoomsForLevel(GameMap.STATION_LEVEL_NAME)) {
                if (r instanceof BrigRoom) {
                    for (Actor a : r.getActors()) {
                        if (gameData.getGameMode().isAntagonist(a)) {
                            antagonistWasInGrig = true;
                        }
                        if (!gameData.getGameMode().isAntagonist(a)) {
                            failed = true;
                        }
                    }
                }
            }
        }
    }
}
