package model.npcs;

import model.map.rooms.Room;
import model.map.rooms.SickbayRoom;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.HealOtherBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.DRBOTCharacter;
import model.npcs.robots.RobotNPC;

public class DRBOTNPC extends RobotNPC {
    public DRBOTNPC(Room room) {
        super(new DRBOTCharacter(room.getID()), new MeanderingMovement(0.0), new DoNothingBehavior(), room);
    }
}
