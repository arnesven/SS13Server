package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.events.animation.AnimatedSprite;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/12/16.
 */
public class PulseRifle extends AmmoWeapon {
    private static final int MAX_AMMO = 18;

    public PulseRifle() {
        super("Pulse Rifle", 0.9, 1.0, false, 1.0, MAX_AMMO, 595);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);
        if (target instanceof Actor) {
            makeAdditionalAttacks(gameData, performingClient, target);
        }
    }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        double emptyness = 1.0 - ((double)getShots()) / getMaxShots();
        int offset = (int)Math.floor(6 * emptyness);
        return new Sprite("pulserifle"+offset, "gun.png", 38 + offset, this);
    }

    private void makeAdditionalAttacks(GameData gameData, Actor performingClient, Target originalTarget) {
        List<Target> alreadyAttacked = new ArrayList<>();
        alreadyAttacked.add(originalTarget);

        List<Target> targets = new ArrayList<>();
        targets.addAll(performingClient.getPosition().getTargets(gameData));
        targets.remove(performingClient);
        while (targets.size() > 6) {
            targets.remove(MyRandom.sample(targets));
        }
        for (Target t : targets) {
            if (!alreadyAttacked.contains(t) && performingClient != t && t.isTargetable()) {
                if (!t.beAttackedBy(performingClient, this, gameData)) {
                    break;
                }
                alreadyAttacked.add(t);
            }
        }
    }

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite sp = new AnimatedSprite("pulseblast", "laser.png",
                0, 4, 32, 32, null, 7, false);

        return sp;
    }
}
