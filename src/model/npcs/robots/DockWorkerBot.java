package model.npcs.robots;

import model.characters.general.GameCharacter;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MovementBehavior;
import model.npcs.behaviors.StayBehavior;

public class DockWorkerBot extends NPC {

    private static int uid_counter;

    public DockWorkerBot(Room r) {
        super(new DockWorkerBotCharacter(uid_counter++, r.getID()), new StayBehavior(), new DoNothingBehavior(), r);
    }

    @Override
    public NPC clone() {
        return new DockWorkerBot(getPosition());
    }
}
