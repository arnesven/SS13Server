package model.characters.decorators;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.Action;
import model.characters.GameCharacter;
import model.events.Damager;
import model.items.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.Room;


/**
 * @author erini02
 * Superclass for character "decorators". A decorator is
 * a class which envelopes the game character. E.g. the host
 * decorator indicates that this character is a host. Other possible
 * decorators could be: "invisible", "burning", "wearing clown clothes".
 */
public abstract class CharacterDecorator extends GameCharacter {


	private GameCharacter innerChar;
	
	public CharacterDecorator(GameCharacter chara, String name) {
		super("name", chara.getStartingRoom(), chara.getSpeed());
		innerChar = chara;
	}

	public GameCharacter getInner() {
		return innerChar;
	}
	
	@Override
	public String getBaseName() {
		return innerChar.getBaseName();
	}
	
	@Override
	public String getPublicName() {
		return innerChar.getPublicName();
	}
	
	@Override
	public String getFullName() {
		return innerChar.getFullName();
	}
	
	@Override
	public Room getStartingRoom(GameData gameData) {
		return innerChar.getStartingRoom(gameData);
	}
	
	@Override 
	public Player getClient() {
		return innerChar.getClient();
	}
	
	@Override
	public void setClient(Player c) {
		innerChar.setClient(c);
	}
	
	@Override
	public Room getPosition() {
		return innerChar.getPosition();
	}
	
	@Override
	public void setPosition(Room room) {
		innerChar.setPosition(room);
	}
	
	@Override
	public boolean checkInstance(InstanceChecker instanceChecker) {
		if (instanceChecker.checkInstanceOf(this)) {
			return true;
		}
		return innerChar.checkInstance(instanceChecker);
	}
	
	
	public void removeInstance(InstanceChecker check) {
		if (getInner() instanceof CharacterDecorator) {
			CharacterDecorator decor = (CharacterDecorator)getInner();
			if (check.checkInstanceOf(decor)) {
				innerChar = decor.getInner();
			} else {
				decor.removeInstance(check);
			}
		} else {
			throw new NoSuchInstanceException("Could not remove instance!");
		}
	}
	
	@Override
	public List<GameItem> getItems() {
		return innerChar.getItems();
	}
	
	@Override
	public boolean isCrew() {
		return innerChar.isCrew();
	}
	
	@Override
	public boolean isDead() {
		return innerChar.isDead();
	}
	
	@Override
	public boolean isInteractable() {
		return innerChar.isInteractable();
	}
	
	@Override
	public boolean beAttackedBy(Actor performingClient, Weapon weapon) {
		return innerChar.beAttackedBy(performingClient, weapon);
	}
	
	@Override
	public void beExposedTo(Actor something, Damager damager) {
		innerChar.beExposedTo(something, damager);
	}
	
	@Override
	public double getHealth() {
		return innerChar.getHealth();
	}
	
	@Override
	public List<GameItem> getStartingItems() {
		return innerChar.getStartingItems();
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		innerChar.addCharacterSpecificActions(gameData, at);
	}
	
	@Override
	public boolean doesPerceive(Action a) {
		return innerChar.doesPerceive(a);
	}
	
	@Override
	public String getKillerString() {
		return innerChar.getKillerString();
	}
	
	@Override
	public double getSpeed() {
		return innerChar.getSpeed();
	}
	
	@Override
	public boolean hasSpecificReaction(GameItem it) {
		return innerChar.hasSpecificReaction(it);
	}
	
	
	@Override
	public void setHealth(double d) {
		innerChar.setHealth(d);
	}
	
	@Override
	public void setKiller(Actor a) {
		innerChar.setKiller(a);
	}
	
	@Override
	public boolean isHealable() {
		return innerChar.isHealable();
	}
	
	@Override
	public int getMovementSteps() {
		return innerChar.getMovementSteps();
	}
	
	@Override
	public SuitItem getSuit() {
		return innerChar.getSuit();
	}
	
	@Override
	public void putOnSuit(SuitItem gameItem) {
		innerChar.putOnSuit(gameItem);
	}
	
	@Override
	public void removeSuit() {
		innerChar.removeSuit();
	}
	
	@Override
	public double getTotalWeight() {
		return innerChar.getTotalWeight();
	}
	
	@Override
	public boolean isEncumbered() {
		System.out.println("is encumbered in decorator");
		return innerChar.isEncumbered();
	}
	
	@Override
	public String getGender() {
		return innerChar.getGender();
	}
	
	@Override
	public double getEncumberenceLevel() {
		System.out.println("Encumberance in decorator");
		return innerChar.getEncumberenceLevel();
	}
	
	@Override
	public void giveItem(GameItem it, Target giver) {
		innerChar.giveItem(it, giver);
	}
	
	@Override
	public String getHowPerceived(Actor actor) {
		return innerChar.getHowPerceived(actor);
	}
	
	@Override
	public Weapon getDefaultWeapon() {
		return innerChar.getDefaultWeapon();
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return innerChar.getIcon(whosAsking);
	}
	
	@Override
	public double getMaxHealth() {
		return innerChar.getMaxHealth();
	}
	
	@Override
	public String getWatchString(Actor whosAsking) {
		return innerChar.getWatchString(whosAsking);
	}
	
	@Override
	public void setMaxHealth(double d) {
		innerChar.setMaxHealth(d);
	}
	
	@Override
	public int getStartingRoom() {
		return innerChar.getStartingRoom();
	}
	
	public void printInstances() {
		System.out.print(this.getClass().getName() + "->");
		getInner().printInstances();
	}
}
