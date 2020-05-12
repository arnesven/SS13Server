package model.npcs.robots;

import model.characters.general.GameCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.*;

public class DockWorkerBot extends NPC {

    private static int uid_counter = 1;

    public DockWorkerBot(Room r) {
        super(new DockWorkerBotCharacter(uid_counter++, r.getID()), new StayBehavior(), new ConcreteReadyForCommandsBehavior(), r);
    }

    @Override
    public NPC clone() {
        return new DockWorkerBot(getPosition());
    }
}
