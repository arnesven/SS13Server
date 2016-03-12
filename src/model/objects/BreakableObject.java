package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.Action;
import model.events.Damager;
import model.items.GameItem;
import model.items.MedKit;
import model.items.weapons.Weapon;

public abstract class BreakableObject extends GameObject implements Target {

	private double hp;
	private double maxHealth;
	private Actor breaker;
	private Weapon brokenByWeapon;
	private String breakString;
	
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
				this.breaker = performingClient;
				this.brokenByWeapon = item;
			}
		} else {
			performingClient.addTolastTurnInfo("You missed the " + super.getName() + ".");
					
		}
	}
	
	@Override
	public String getName() {
		if (isBroken()) {
			return super.getName() + " (broken)";
		} else if (hp < maxHealth) {
			return super.getName() + " (damaged)";
		}
		return super.getName();
	}
	
	@Override
	public final void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
		if (!isBroken()) {
			addActions(gameData, cl, at);
		}
	}
	
	protected abstract void addActions(GameData gameData, Player cl, ArrayList<Action> at);

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

	@Override
	public void beExposedTo(Actor performingClient, Damager damage) {
		if (damage.isDamageSuccessful(false)) {
			hp = Math.max(0.0, hp - damage.getDamage());
			if (hp == 0) { // broken :-)
				if (performingClient != null) {
					breaker = performingClient;
				} else {
					breakString = damage.getName();
				}
			}
		}
	}

	public boolean isDamaged() {
		return hp < maxHealth && hp > 0;
	}

	public Actor getBreaker() {
		return breaker;
	}
	
	public String getBreakString() {
		if (breaker != null) {
			return breaker.getBaseName() + " with " + brokenByWeapon.getName();
		} else {
			return breakString;
		}
	}

	@Override
	public boolean hasInventory() {
		return false;
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
}
