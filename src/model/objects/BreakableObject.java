package model.objects;

import java.util.List;

import model.Actor;
import model.Player;
import model.actions.Target;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Weapon;

public class BreakableObject extends GameObject implements Target {

	private double hp;
	private double maxHealth;
	
	public BreakableObject(String name, double starthp) {
		super(name);
		this.hp = starthp;
		maxHealth = starthp;
	}

	@Override
	public void beAttackedBy(Actor performingClient, Weapon item) {
		
		if (item.isAttackSuccessful(false)) {
			hp = Math.max(0.0, hp - item.getDamage());
			performingClient.addTolastTurnInfo("You " + item.getSuccessfulMessage() + "ed the " + super.getName() + ".");
			if (isBroken()) {
				performingClient.addTolastTurnInfo("The " + super.getName() + " was destroyed!");				
			}
		} else {
			performingClient.addTolastTurnInfo("You missed the " + super.getName() + ".");
					
		}
	}

	public boolean isBroken() {
		return hp == 0.0;
	}

	@Override
	public boolean isTargetable() {
		return true;
	}

	@Override
	public boolean isDead() {
		return hp <= 0;
	}

	@Override
	public double getHealth() {
		return hp;
	}

	@Override
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setHealth(double d) {
		hp = d;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToHealth(double d) {
		hp = Math.min(getMaxHealth(), hp + d);
		
	}

	@Override
	public List<GameItem> getItems() {
		return null;
	}
	

}
