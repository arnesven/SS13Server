package model.npcs;

import model.map.rooms.Room;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.RecyclotronCharacter;
import model.npcs.robots.RobotNPC;

public class RecyclotronNPC extends RobotNPC {
    public RecyclotronNPC(Room r) {
        super(new RecyclotronCharacter(r), new MeanderingMovement(0.0), new DoNothingBehavior(), r);
    }
}
