package model.characters.decorators;

import java.util.ArrayList;
import java.util.List;

import graphics.OverlaySprite;
import graphics.sprites.PhysicalBody;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.events.damage.Damager;
import model.items.foods.FoodItem;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.map.rooms.RoomType;
import model.movepowers.MovePowersHandler;
import model.npcs.behaviors.ActionBehavior;
import util.Logger;


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
	
	public void setInner(GameCharacter in) {
		this.innerChar = in;
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

    public String getPublicName(Actor whosAsking) {
        return innerChar.getPublicName(whosAsking);
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
	public Actor getActor() {
		return innerChar.getActor();
	}
	
	@Override
	public void setActor(Actor c) {
		innerChar.setActor(c);
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

            // TODO::: fix..
            System.err.println("CRITICAL! Could not remove isntance! " + check.getClass());
            //throw new NoSuchInstanceException("Could not remove instance!");
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
    public Actor getKiller() {
        return innerChar.getKiller();
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
		return innerChar.isEncumbered();
	}
	
	@Override
	public String getGender() {
		return innerChar.getGender();
	}
	
	@Override
	public void setGender(String gen) {
		innerChar.setGender(gen);
	}

	
	@Override
	public double getEncumberenceLevel() {
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
    public Sprite getSprite(Actor whosAsking) {
        return innerChar.getSprite(whosAsking);
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
	
	@Override
	public GameCharacter clone() {
        return innerChar.clone();
	}

    @Override
	public void printInstances() {
		Logger.log(Logger.CRITICAL,
                this.getClass().getName() + "->");
		getInner().printInstances();
	}

	@Override
	public boolean canUseObjects() {
		return innerChar.canUseObjects();
	}

    @Override
    public boolean hasInventory() {
        return innerChar.hasInventory();
    }

    @Override
    public boolean isAttackerSuccessful(Actor performingClient, Weapon weapon) {
        return innerChar.isAttackerSuccessful(performingClient, weapon);
    }

    @Override
    public boolean doDamageOnSelfWith(Weapon weapon) {
        return innerChar.doDamageOnSelfWith(weapon);
    }

    @Override
    public void doUponDeath(Actor killer, GameItem killItem) {
        innerChar.doUponDeath(killer, killItem);
    }

    @Override
    public boolean wasCriticalHit(Weapon weapon) {
        return innerChar.wasCriticalHit(weapon);
    }

    @Override
    public char getIcon(Player whosAsking) {
        return innerChar.getIcon(whosAsking);
    }

    @Override
    public boolean getsAttackOfOpportunity(Weapon w) {
        return innerChar.getsAttackOfOpportunity(w);
    }

    @Override
    public Sprite getNakedSprite() {
        return innerChar.getNakedSprite();
    }

    @Override
    public boolean isPassive() {
        return innerChar.isDead();
    }

    @Override
    public boolean getsActions() {
        return !isDead();
    }

    @Override
    public boolean isVisible() {
        return innerChar.isVisible();
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return innerChar.getOverlayStrings(player, gameData);
    }

    @Override
    public void doBeforeMovement(GameData gameData) {
        innerChar.doBeforeMovement(gameData);
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        innerChar.doAfterMovement(gameData);
    }

    @Override
    public void doAfterActions(GameData gameData) {
        innerChar.doAfterActions(gameData);
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        innerChar.doAtEndOfTurn(gameData);
    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor anyActorInRoom,
                                          ArrayList<Action> at) {
        innerChar.addActionsForActorsInRoom(gameData, anyActorInRoom, at);
    }

    public List<Room> getVisibleMap(GameData gameData) {
        if (getPosition().getType() == RoomType.outer) {
            List<Room> arr = new ArrayList<>();
            arr.add(getPosition());
            return arr;
        }

        return innerChar.getVisibleMap(gameData);
    }

    @Override
    public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {
        innerChar.doWhenConsumeItem(foodItem, gameData);
    }

    @Override
    public void moveDecoratorsAndCopy(GameCharacter selectedJob) {
        innerChar.moveDecoratorsAndCopy(selectedJob);
        if (!(innerChar instanceof CharacterDecorator)) {
            // next char is the REAL character
            this.innerChar = selectedJob;
        }
    }

    @Override
    public GameItem getKillerItem() {
        return innerChar.getKillerItem();
    }

    public void modifyActionList(GameData gameData, ArrayList<Action> at) { innerChar.modifyActionList(gameData, at);};

    @Override
    public ActionBehavior getDefaultActionBehavior() {
        return innerChar.getDefaultActionBehavior();
    }

    public void addMovepowersButtons(List<Room> result, GameData gameData, MovePowersHandler mp) {
        innerChar.addMovepowersButtons(result, gameData, mp);
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return innerChar.isVisibileFromAdjacentRoom();
    }

    @Override
    public PhysicalBody getPhysicalBody() {
        return innerChar.getPhysicalBody();
    }

    @Override
    public void setNakedness(PhysicalBody nakedSprite) {
        innerChar.setNakedness(nakedSprite);
    }

    public CharacterDecorator getDecorator(InstanceChecker check) {
            if (check.checkInstanceOf(this)) {
                return this;
            } else {
                return getInner().getDecorator(check);
            }
    }
}
