package model.npcs;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Target;
import model.characters.GameCharacter;
import model.characters.InfectedCharacter;
import model.characters.InstanceChecker;
import model.items.GameItem;
import model.items.MedKit;
import model.items.Weapon;
import model.map.Room;

/**
 * @author erini02
 * Class representing NPCs on the station (non-player characters).
 * E.g. monsters, crewmembers not controlled by players. And other things.
 */
public class NPC extends Actor implements Target {
		
	private MovementBehavior moveBehavior;
	private ActionBehavior actBehavior;
	private double maxHealth;
	
	public NPC(GameCharacter chara, MovementBehavior m, ActionBehavior a, Room r) {
		this.setCharacter(chara);
		moveBehavior = m;
		actBehavior = a;
		this.setHealth(1.0);
		this.setMaxHealth(1.0);
		moveIntoRoom(r);
	}
	
	protected void moveIntoRoom(Room r) {
		if (getPosition() != null) {
			getPosition().removeNPC(this);
		}
		r.addNPC(this);
		setPosition(r);
	}
	
	public void moveAccordingToBehavior() {
		if (!isDead()) {
			moveBehavior.move(this);
		}
	}


	public void actAccordingToBehavior(GameData gameData) {
		if (!isDead()) {
			actBehavior.act(this, gameData);
		}
	}

	public String getName() {
		return getCharacter().getFullName();
	}

	@Override
	public boolean isTargetable() {
		return !isDead();
	}

	@Override
	public void beAttackedBy(Actor performingClient, Weapon item) {
		getCharacter().beAttackedBy(performingClient, item);
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
		return this.maxHealth;
	}
	
	public void setMaxHealth(double d) {
		this.maxHealth = d;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToHealth(double d) {
		getCharacter().setHealth(Math.min(getMaxHealth(), 
				 getCharacter().getHealth() + d));
	}

	public void beInfected(Actor performingClient) {
		this.setCharacter(new InfectedCharacter(this.getCharacter(), performingClient));
		this.actBehavior = new AttackIfPossibleBehavior(new Weapon("Fists", 0.5, 0.5, false));
		this.moveBehavior = new MeanderingMovement(0.75);
	}
	


}
