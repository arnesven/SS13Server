package model.npcs;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.BAR2D2Character;
import model.characters.general.RobotCharacter;
import model.map.Room;
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
