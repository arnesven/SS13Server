package model.npcs;

import model.Actor;
import model.GameData;
import model.Target;
import model.characters.general.GameCharacter;
import model.characters.decorators.InfectedCharacter;
import model.events.damage.Damager;
import model.items.NoSuchThingException;
import model.items.general.MedKit;
import model.map.rooms.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author erini02
 * Class representing NPCs on the station (non-player characters).
 * E.g. monsters, crewmembers not controlled by players. And hidden things.
 */
public abstract class NPC extends Actor implements Target, Serializable {
		
	private MovementBehavior moveBehavior;
	private ActionBehavior actBehavior;
	private boolean cancel = false;
	
	public NPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r, double startHP, double startMaxHP) {
		this.setCharacter(chara);
		moveBehavior = m;
		actBehavior = a;
		this.setHealth(startHP);
		this.setMaxHealth(startMaxHP);
		moveIntoRoom(r);
	}

	public NPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r) {
		this(chara, m, a, r, 1.0, 1.0);
	}


    public void moveIntoRoom(Room r) {
		if (getPosition() != null) {
            try {
                getPosition().removeNPC(this);
            } catch (NoSuchThingException e) {
                Logger.log(Logger.CRITICAL, "Tried removing NPC from room but it wasn't there!");
            }
        }
		r.addNPC(this);
		setPosition(r);
	}
	
	public void moveAccordingToBehavior() {
		if (!isDead() && getCharacter().getMovementSteps() > 0) {
			moveBehavior.move(this);
		}
	}

	public void setCancelled(boolean b) {
		this.cancel  = b;
	}
	
	public void actAccordingToBehavior(GameData gameData) {
		if (!isDead()) {
			if (cancel) {
				cancel = false;
			} else {
				actBehavior.act(this, gameData);
			}
		}
	}

	public String getName() {
		return getPublicName();
	}

	@Override
	public boolean isTargetable() {
		return !isDead() && getCharacter().isVisible();
	}



	@Override
	public boolean isDead() {
		return getCharacter().isDead();
	}

	public boolean shouldBeCleanedUp() {
		return false;
	}

	@Override
	public double getHealth() {
		return getCharacter().getHealth();
	}

	@Override
	public double getMaxHealth() {
		return this.getCharacter().getMaxHealth();
	}
	
	public void setMaxHealth(double d) {
		this.getCharacter().setMaxHealth(d);
	}
	
	public void setHealth(double d) {
		getCharacter().setHealth(d);
	}

	@Override
	public void addTolastTurnInfo(String string) {
		// Not needed for NPCs
	}

	@Override
	public Target getAsTarget() {
		return this;
	}
	
	@Override
	public void action(GameData gameData) {
		this.actAccordingToBehavior(gameData);
	}


	@Override
	public boolean hasSpecificReaction(MedKit objectRef) {
		return false;
	}



	public void beInfected(Actor performingClient) {
		this.setCharacter(new InfectedCharacter(this.getCharacter(), performingClient));
		this.actBehavior = new AttackAllActorsButNotTheseClasses(new ArrayList<>());
		this.moveBehavior = new MeanderingMovement(0.75);
	}

	@Override
	public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
		getCharacter().beExposedTo(performingClient, damage);
	}

	
	@Override
	public boolean isHealable() {
		return getCharacter().isHealable();
	}

	public void setActionBehavior(ActionBehavior behavior) {
		this.actBehavior = behavior;
	}
	
	public void setMoveBehavior(MovementBehavior behavior) {
		this.moveBehavior = behavior;
	}

	public MovementBehavior getMovementBehavior() {
		return this.moveBehavior;
	}

    public ActionBehavior getActionBehavior() {
        return this.actBehavior;
    }

	@Override
	public boolean canBeInteractedBy(Actor performingClient) {
		return this.getPosition() == performingClient.getPosition();
		
	}

    public abstract NPC clone();

}
