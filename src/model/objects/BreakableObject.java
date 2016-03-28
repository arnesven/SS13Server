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
import model.map.Room;

public abstract class BreakableObject extends GameObject implements Target {

	private double hp;
	private double maxHealth;
	private Actor breaker;
	private Weapon brokenByWeapon;
	private String breakString;
	
	public BreakableObject(String name, double starthp, Room r) {
		super(name, r);
		this.hp = starthp;
		maxHealth = starthp;
	}

	@Override
	public String getName() {
		return getBaseName();
	}
	
	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon item) {
		boolean success;
		if (item.isAttackSuccessful(false)) {
			success = true;
			hp = Math.max(0.0, hp - item.getDamage());
			performingClient.addTolastTurnInfo("You " + item.getSuccessfulMessage() + "ed the " + super.getPublicName(performingClient) + ".");
			if (isBroken()) {
				performingClient.addTolastTurnInfo("The " + super.getPublicName(performingClient) + " was destroyed!");				
				this.breaker = performingClient;
				this.brokenByWeapon = item;
			}
		} else {
			performingClient.addTolastTurnInfo("You missed the " + super.getPublicName(performingClient) + ".");
			success = false;
		}
		return success;
	}
	
	@Override
	public String getPublicName(Actor whosAsking) {
		if (isBroken()) {
			return super.getPublicName(whosAsking) + " (broken)";
		} else if (hp < maxHealth) {
			return super.getPublicName(whosAsking) + " (damaged)";
		}
		return super.getPublicName(whosAsking);
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
		return !isBroken();
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
	
	protected void setMaxHealth(double d) {
		this.maxHealth = d;
	}

	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
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
			return breaker.getBaseName() + " with " + brokenByWeapon.getBaseName();
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
