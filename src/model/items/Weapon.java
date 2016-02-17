package model.items;

import java.util.Random;

public class Weapon extends GameItem {

	public static Random random = new Random();
	private double hitChance;
	private double damage;
	private boolean makesBang;
	
	public Weapon(String string, double hitChance, double damage, boolean bang) {
		super(string);
		this.hitChance = hitChance;
		this.damage = damage;
		makesBang = bang;
	}

	public boolean isAttackSuccessful() {
		return random.nextDouble() < getHitChance();
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
