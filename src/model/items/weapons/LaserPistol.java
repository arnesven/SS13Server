package model.items.weapons;


import graphics.ExtraEffect;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.*;
import model.events.ambient.ElectricalFire;
import model.events.animation.AnimatedSprite;
import model.items.TraitorItem;
import sounds.Sound;
import util.Logger;
import util.MyRandom;

import java.awt.*;

public class LaserPistol extends AmmoWeapon implements TraitorItem {

	public LaserPistol() {
		super("Laser pistol", 0.90, 1.0, false, 1.0, 6, 340);
        this.setCriticalChance(0.15);
	}

	@Override
	public LaserPistol clone() {
		return new LaserPistol();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("laserpistol", "gun.png", 8, this);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("laserpistolinhand", "items_righthand.png", 0, 16, this);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);
    }

    @Override
    protected void checkOnlyMissHazard(final Actor performingClient, GameData gameData, Target originalTarget) {
	    super.checkOnlyMissHazard(performingClient, gameData, originalTarget);
        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.05) {
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    Logger.log(Logger.INTERESTING,
                            performingClient.getBaseName() + " started a fire!");
                }
            }
        };

    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("http://www.ida.liu.se/~erini02/ss13/laser_a.ogg");
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A modern Nanotrasen firearm. WARNING: High intensity laser! Keep away from your eyes.";
    }

    @Override
    public int getTelecrystalCost() {
        return 3;
    }

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
	    AnimatedSprite beamSprite = new AnimatedSprite("laserbeamred", "laser.png", 1, 2, 32, 32, null, 7, false);
        beamSprite.setColor(Color.RED);
        return beamSprite;
    }
}
