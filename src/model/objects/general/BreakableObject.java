package model.objects.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

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
	public boolean canBeInteractedBy(Actor performingClient) {
		return this.getPosition() == performingClient.getPosition();
		
	}

    public void setBreaker(Actor a) {
        this.breaker = a;
    }

	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon item) {
		boolean success;
		boolean alreadyBroken = isBroken();
		if (item.isAttackSuccessful(false)) {
			success = true;
			hp = Math.max(0.0, hp - item.getDamage());
			performingClient.addTolastTurnInfo("You " + item.getSuccessfulMessage() + "ed the " + super.getPublicName(performingClient) + ".");
			if (isBroken() && !alreadyBroken) {
				performingClient.addTolastTurnInfo("The " + super.getPublicName(performingClient) + " was destroyed!");				
				this.breaker = performingClient;
				this.brokenByWeapon = item;
				thisJustBroke();
			}
		} else {
			performingClient.addTolastTurnInfo("You missed the " + super.getPublicName(performingClient) + ".");
			success = false;
		}
		return success;
	}
	
	public void thisJustBroke() {
		// can be overridden for fun
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
	public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (!isBroken()) {
			addActions(gameData, cl, at);
		}
	}
	
	protected abstract void addActions(GameData gameData, Actor cl, ArrayList<Action> at);

	public boolean isBroken() {
		return hp == 0.0;
	}

	@Override
	public boolean isTargetable() {
		return true; //!isBroken();
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
        if (d > 0) {
            hp = Math.min(getMaxHealth(), hp + d);
        } else {
            hp = Math.max(0.0, hp + d);
        }
	}

	@Override
	public List<GameItem> getItems() {
		return null;
	}

	@Override
	public void beExposedTo(Actor performingClient, Damager damage) {
		boolean alreadyBroken = isBroken();
		if (damage.isDamageSuccessful(false)) {
            damage.doDamageOnMe(this);
        	if (hp == 0) { // broken :-)
                if (!alreadyBroken) {
                    thisJustBroke();
                }
				if (performingClient != null && !alreadyBroken) {
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
            String str = breaker.getBaseName();
            if (brokenByWeapon != null) {
                str += " with " + brokenByWeapon.getBaseName();
            }
            return str;
        } else if (brokenByWeapon != null) {
            return brokenByWeapon.getBaseName();

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

    public boolean canBeDismantled() {
        return true;
    }

	@Override
	public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
		List<Action> actions = super.getOverlaySpriteActionList(gameData, r, forWhom);
		if (r == forWhom.getPosition()) {
			if (forWhom.getsActions()) {
				AttackAction atk = new AttackAction(forWhom);
				if (atk.isAmongOptions(gameData, forWhom, this.getBaseName())) {
					atk.stripAllTargetsBut(this);
					atk.addClientsItemsToAction(forWhom);
					actions.add(atk);
				}
			}
		}
		return actions;
	}

	public boolean isLootable() {
		return false;
	}
}
