package model.npcs.robots;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.characters.general.AnimatedSlotMachineCharacter;
import model.items.general.MoneyStack;
import model.map.rooms.Room;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class AnimatedSlotMachineNPC extends RobotNPC {
    public AnimatedSlotMachineNPC(Room position) {
        super(new AnimatedSlotMachineCharacter(position.getID()),
                new MeanderingMovement(0.75), new SlotMachineBehavior(), position);
    }

    private static class SlotMachineBehavior extends AttackAllActorsNotSameClassBehavior {
        @Override
	    public void act(Actor npc, GameData gameData) {
            AttackAction atk = new AttackAction(npc);
            List<Target> targets = getTargets(npc, gameData, atk);
            super.act(npc, gameData);

            if (targets.size() > 0) {
                npc.getPosition().addItem(new MoneyStack((int)(Math.pow(2.0, (double)targets.size()-1)) + MyRandom.nextInt(10)));
            }
            for (Actor a : npc.getPosition().getActors()) {
                a.addTolastTurnInfo("Money came out of the slot machine.");
            }
        }
    }
}
