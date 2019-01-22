package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.items.general.GameItem;
import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;


public abstract class Weapon extends GameItem {

	public static final Weapon FISTS      = new Fist();
    public static final Weapon TENTACLE   = new Tentacle();
    public static final Weapon STEEL_PROD = new SteelProd();
    public static final Weapon TEETH      = new Teeth();
    public static final Weapon TUSKS      = new Tusks();
    public static final Weapon FLYING_CREDIT = new FlyingCredit();
    public static Weapon CLAWS            = new Claws();
    public static Weapon BEAR_CLAWS       = new BearClaws();

    private double criticalChance = 0.05;
    private double hitChance;
	private double damage;
	private boolean makesBang;
    private double lastRoll;
    private boolean attackOfOpportunity;

    public Weapon(String string, double hitChance, double damage,
                  boolean bang, double weight, boolean attackOfOp, int cost) {
        super(string, weight, cost);
        this.hitChance = hitChance;
        this.damage = damage;
        makesBang = bang;
        attackOfOpportunity = attackOfOp;
    }

    public Weapon(String string, double hitChance, double damage,
				  boolean bang, double weight, int cost) {
        this(string, hitChance, damage, bang, weight, false, cost);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("weapon", "gun.png", 0);
    }

    public boolean isAttackSuccessful(boolean reduced) {
        lastRoll = MyRandom.nextDouble();
        if (reduced) {
       		return lastRoll < getHitChance()*0.5;
		}
		return lastRoll < getHitChance();
	}

	protected double getHitChance() {
		return hitChance;
	}
	
	public double getDamage() {
		return damage;
	}

	public String getSuccessfulMessage() {
		return "attack";
	}

	public SensoryLevel getSensedAs() {
		if (!makesBang) {
			return SensoryLevel.PHYSICAL_ACTIVITY;
		}
		
		return new SensoryLevel(VisualLevel.CLEARLY_VISIBLE, 
								AudioLevel.VERY_LOUD, 
								OlfactoryLevel.UNSMELLABLE);
	}

	public boolean isReadyToUse() {
		return true;
	}



	protected boolean makesBang() {
		return makesBang;
	}

	/**
	 * This method is called when the weapon has
	 * successfully hit the target.
	 * @param performingClient
	 */
	protected void usedOnBy(Target target, Actor performingClient,
			GameData gameData) { }


    protected void checkHazard(Actor performingClient, GameData gameData) { }

    protected void checkOnlyMissHazard(Actor performingClient, GameData gameData) { }


//    @Override
//	public Weapon clone() {
//		return new Weapon(this.getBaseName(), this.getHitChance(),
//						  this.getDamage(), this.makesBang, this.getWeight(), getCost());
//	}



	public void setDamage(double d) {
		this.damage = d;
	}


    public boolean wasCriticalHit() {
        return lastRoll <= criticalChance;
    }

    protected void setCriticalChance(double newChance) {
        this.criticalChance = newChance;
    }

    public void dealDamageOnMe(Actor actor) {
        actor.subtractFromHealth(this.getDamage());
    }

    public void dealCriticalDamageOnMe(Actor actor) {
        actor.subtractFromHealth((this.getDamage()*2));
    }


    public boolean givesAttackOfOpportunity() {
        return attackOfOpportunity;
    }

    public void doAttack(Actor performingClient, Target target, GameData gameData) {
        boolean success = target.beAttackedBy(performingClient, this);
        if (success) {
            usedOnBy(target, performingClient, gameData);
        } else {
            checkOnlyMissHazard(performingClient, gameData);
        }
        checkHazard(performingClient, gameData);
    }

    public String getCriticalMessage() {
        List<String> messages = new ArrayList<>();
        messages.add("Took out an eye");
        messages.add("Cut an artery");
        messages.add("Broke a nose");
        messages.add("Broke a rib");
        messages.add("Hit the skull");
        messages.add("Splintered a jaw");
        messages.add("Pierced a lung");
        messages.add("Struck the groin");
        messages.add("Some guts came out");
        messages.add("Blood shoots out");
        messages.add("Broke an arm");
        messages.add("Hit the head");
        messages.add("Crushed an elbow");
        messages.add("Crushed a hand");
        messages.add("Right in the mouth");
        return MyRandom.sample(messages);
    }


    public Sprite getHandHeldSprite() {
        return new Sprite("weaponinhand", "items_righthand.png", 0, 4);
    }

    public void setHitChance(double hitChance) {
        this.hitChance = hitChance;
    }

    public boolean hasRealSound() {
        return false;
    }

    public Sound getRealSound() {
        return null;
    }

    public double getAmpChance() {
        return 0.25;
    }
}
