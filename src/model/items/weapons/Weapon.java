package model.items.weapons;

import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;
import util.MyRandom;


public class Weapon extends GameItem {

	private double hitChance;
	private double damage;
	private boolean makesBang;
	
	public Weapon(String string, double hitChance, double damage,
				  boolean bang, double weight) {
		super(string, weight);
		this.hitChance = hitChance;
		this.damage = damage;
		makesBang = bang;
	}

	public boolean isAttackSuccessful(boolean reduced) {
		if (reduced) {
			return MyRandom.nextDouble() < getHitChance()*0.5;
		}
		return MyRandom.nextDouble() < getHitChance();
	}

	private double getHitChance() {
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


}
