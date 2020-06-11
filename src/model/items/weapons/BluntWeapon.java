package model.items.weapons;


import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.objects.general.BreakableObject;
import sounds.Sound;
import util.MyRandom;

public abstract class BluntWeapon extends Weapon implements BludgeoningWeapon {

    private double durability;

    public BluntWeapon(String string, double weight, int cost, double durablility) {
		super(string, 0.65, 1.0, false, weight, cost);
        this.durability = durablility;
	}

    @Override
    public void doAttack(Actor performingClient, Target target, GameData gameData) {
        super.doAttack(performingClient, target, gameData);
        double factor = 1.0;
        if (target instanceof BreakableObject) {
            factor = 0.85;
        }
        if (performingClient.getItems().contains(this) && MyRandom.nextDouble() > getDurability()*factor) {
            performingClient.addTolastTurnInfo("The " + this.getPublicName(performingClient) + " was broken.");
            performingClient.getItems().remove(this);
        }
    }

    public double getDurability() {
        return durability;
    }

    @Override
    public double getAmpChance() {
        return 0.05;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return " or whacking somebody in the head.";
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("genhit" + (MyRandom.nextInt(4) + 1));
    }

    @Override
    public Sound getDropSound() {
        return new Sound("crowbar_drop");
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("crowbar_pickup");
    }
}
