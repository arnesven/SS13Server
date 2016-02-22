package model.objects;

import model.Client;
import model.actions.ActionPerformer;
import model.actions.Target;
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
	public void beAttackedBy(ActionPerformer performingClient, Weapon item) {
		
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
	

}
