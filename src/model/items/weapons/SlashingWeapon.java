package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.itemactions.CutOutBrainAction;
import model.items.Brain;
import util.MyRandom;

/**
 * Created by erini02 on 25/08/17.
 */
public abstract class SlashingWeapon extends Weapon {
    public SlashingWeapon(String string, double hitChance, double damage, boolean bang, double weight, boolean attackOfOp, int cost) {
        super(string, hitChance, damage, bang, weight, attackOfOp, cost);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);

        if (target.isDead() && target instanceof Actor) {
            if (CutOutBrainAction.canHaveBrainCutOut((Actor)target)) {
                if (MyRandom.nextDouble() < 0.33) {
                    Brain b = CutOutBrainAction.cutOutABrainFrom(performingClient, (Actor)target);
                    performingClient.getPosition().addItem(b);
                    performingClient.addTolastTurnInfo("Whoa! The brain came out - gross!");
                }
            }
        }

    }
}
