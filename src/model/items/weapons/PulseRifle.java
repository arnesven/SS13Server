package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;

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
            List<Actor> alreadyAttacked = new ArrayList<>();
            alreadyAttacked.add((Actor)target);
            makeAdditionalAttacks(gameData, performingClient, alreadyAttacked);
        }
    }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        double emptyness = 1.0 - ((double)getShots()) / getMaxShots();
        int offset = (int)Math.floor(6 * emptyness);
        return new Sprite("pulserifle"+offset, "gun.png", 38 + offset, this);
    }

    private void makeAdditionalAttacks(GameData gameData, Actor performingClient, List<Actor> alreadyAttacked) {
        for (Actor a : performingClient.getPosition().getActors()) {
            if (!alreadyAttacked.contains(a) && performingClient != a && a.getAsTarget().isTargetable()) {
                if (!a.getAsTarget().beAttackedBy(performingClient, this, gameData)) {
                    break;
                }
                alreadyAttacked.add(a);
            }
        }
    }
}
