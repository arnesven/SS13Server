package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.events.ambient.ElectricalFire;
import model.items.general.FireExtinguisher;
import model.npcs.NPC;

/**
 * Created by erini02 on 15/04/16.
 */
public class PutOutFireBehavior implements ActionBehavior {
    private final GameData gameData;

    public PutOutFireBehavior(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void act(NPC npc, GameData gameData) {

        ElectricalFire fire = FireExtinguisher.getFire(npc.getPosition());

        if (fire == null) {
            System.out.println("What, no fire?");
            return;
        }
        fire.fix();

        for (Actor a : npc.getPosition().getActors()) {
            if (a != npc) {
                a.addTolastTurnInfo(npc.getPublicName() + " put out the fire!");
            }
        }

    }
}
