package model.npcs;

import model.characters.general.RobotCharacter;
import model.characters.special.DoomsdayMachineCharacter;
import model.items.CosmicMonolith;
import model.map.rooms.Room;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.DoomsdayMachineBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.robots.RobotNPC;

import java.util.List;

public class DoomsdayMachineNPC extends RobotNPC {
    public DoomsdayMachineNPC(Room r, CosmicMonolith cm) {
        super(new DoomsdayMachineCharacter(cm, r.getID()), new MeanderingMovement(0.35),
                new DoomsdayMachineBehavior(), r);

    }
}
