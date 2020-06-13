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
    private int lastAdditionalAttacksIn = 0;

    public PulseRifle() {
        super("Pulse Rifle", 0.9, 1.0, false, 1.0, MAX_AMMO, 595);
    }


    @Override
    public boolean doAttack(Actor performingClient, Target target, GameData gameData) {
        boolean result = super.doAttack(performingClient, target, gameData);
        if ( lastAdditionalAttacksIn != gameData.getRound()) {
            lastAdditionalAttacksIn = gameData.getRound();
            makeAdditionalAttacks(gameData, performingClient, target);
        }
        return result;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("pulserifle", "gun.png", 38, this);
    }

    private void makeAdditionalAttacks(GameData gameData, Actor performingClient, Target originalTarget) {
        List<Target> alreadyAttacked = new ArrayList<>();
        alreadyAttacked.add(originalTarget);

        List<Target> targets = new ArrayList<>();
        targets.addAll(performingClient.getPosition().getTargets(gameData));
        targets.remove(performingClient);
        double originalHitChance = getHitChance();
        while (targets.size() > 6) {
            targets.remove(MyRandom.sample(targets));
        }
        for (Target t : targets) {
            if (!alreadyAttacked.contains(t) && performingClient != t && t.isTargetable()) {
                setHitChance(getHitChance()*0.85);
                doAttack(performingClient, t, gameData);
                alreadyAttacked.add(t);
            }
        }
        setHitChance(originalHitChance);
    }

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite sp = new AnimatedSprite("pulseblast", "laser.png",
                0, 4, 32, 32, null, 8, false);
        return sp;
    }
}
