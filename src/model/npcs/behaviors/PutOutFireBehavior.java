package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.events.NoSuchEventException;
import model.events.ambient.ElectricalFire;
import model.items.general.FireExtinguisher;
import util.Logger;

/**
 * Created by erini02 on 15/04/16.
 */
public class PutOutFireBehavior implements ActionBehavior {
    private final GameData gameData;

    public PutOutFireBehavior(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        try {
            ElectricalFire fire = FireExtinguisher.getFire(npc.getPosition());

            if (fire == null) {
                Logger.log(Logger.INTERESTING, "What, no fire?");
                return;
            }
            fire.fix();
        } catch (NoSuchEventException nse) {
            Logger.log(Logger.INTERESTING, "What, no fire?");
            return;
        }

        for (Actor a : npc.getPosition().getActors()) {
            if (a != npc) {
                a.addTolastTurnInfo(npc.getPublicName() + " put out the fire!");
            }
        }

    }
}
