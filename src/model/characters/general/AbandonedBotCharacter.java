package model.characters.general;

import model.map.Room;

/**
 * Created by erini02 on 25/11/16.
 */
public class AbandonedBotCharacter extends RobotCharacter {
    public AbandonedBotCharacter(Room derelictGenerator) {
        super("ABRANDON", derelictGenerator.getID(), 20.0);
    }
}
