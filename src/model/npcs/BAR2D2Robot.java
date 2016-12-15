package model.npcs;

import model.characters.general.BAR2D2Character;
import model.map.rooms.Room;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.TellRumorsBehavior;
import model.npcs.robots.RobotNPC;

/**
 * Created by erini02 on 15/11/16.
 */
public class BAR2D2Robot extends RobotNPC {
    public BAR2D2Robot(int id, Room bar) {
        super(new BAR2D2Character(id),
                new MeanderingMovement(0.0), new TellRumorsBehavior(), bar);
    }

}
