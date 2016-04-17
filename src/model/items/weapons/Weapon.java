package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.items.general.GameItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;


public class Weapon extends GameItem {

	public static final Weapon FISTS = new Weapon("Fists", 0.5, 0.5, false, 0.0, true);
	public static Weapon CLAWS = new Weapon("Claws", 0.75, 0.5, false, 0.0, true);

    private double criticalChance = 0.05;
    private double hitChance;
	private double damage;
	private boolean makesBang;
    private double lastRoll;
    private boolean attackOfOpportunity;

    public Weapon(String string, double hitChance, double damage,
                  boolean bang, double weight, boolean attackOfOp) {
        super(string, weight);
        this.hitChance = hitChance;
        this.damage = damage;
        makesBang = bang;
        attackOfOpportunity = attackOfOp;
    }

    public Weapon(String string, double hitChance, double damage,
				  boolean bang, double weight) {
        this(string, hitChance, damage, bang, weight, false);
	}
	
	@Override
	protected char getIcon() {
		return 'w';
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
	public void usedOnBy(Target target, Actor performingClient,
			GameData gameData) {
		
	}

	@Override
	public Weapon clone() {
		return new Weapon(this.getBaseName(), this.getHitChance(), 
						  this.getDamage(), this.makesBang, this.getWeight());
	}

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
        }
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
        messages.add("Severed an arm");
        messages.add("Broke an arm");
        messages.add("Hit the head");
        messages.add("Crushed an elbow");
        messages.add("Crushed a hand");
        messages.add("Right in the mouth");
        return MyRandom.sample(messages);
    }


}
