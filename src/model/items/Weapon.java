package model.items;

import util.MyRandom;


public class Weapon extends GameItem {

	private double hitChance;
	private double damage;
	private boolean makesBang;
	
	public Weapon(String string, double hitChance, double damage, boolean bang) {
		super(string);
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


}
