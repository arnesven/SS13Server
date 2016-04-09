package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.items.GameItem;
import util.MyRandom;


public class Weapon extends GameItem {

	public static final Weapon FISTS = new Weapon("Fists", 0.5, 0.5, false, 0.0);
	public static Weapon CLAWS = new Weapon("Claws", 0.75, 0.5, false, 0.0);
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
	
	@Override
	protected char getIcon() {
		return 'w';
	}

	public boolean isAttackSuccessful(boolean reduced) {
		if (reduced) {
			return MyRandom.nextDouble() < getHitChance()*0.5;
		}
		return MyRandom.nextDouble() < getHitChance();
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


}
