package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.characteractions.*;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.NoSuchInstanceException;
import model.items.general.GameItem;
import model.items.general.TornClothes;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.Room;
import model.npcs.HumanNPC;
import model.objects.general.GameObject;
import util.Logger;

public class ChangelingCharacter extends GameCharacter {
	
	private List<GameCharacter> forms = new ArrayList<>();
	private GameCharacter current;
	private Room startRoom;
	private HorrorCharacter ultimate = new HorrorCharacter();
    private boolean acidSprayed = false;

    public ChangelingCharacter(Room startRoom) {
		super("Changeling", startRoom.getID(), 21.0);
		this.startRoom = startRoom;
		setMaxHealth(2.0);
		setHealth(2.0);
		current = new ParasiteCharacter();
		forms.add(current);
	}

	@Override
	public List<GameItem> getStartingItems() {
		return new ArrayList<>();
	}
	
	@Override
	public String getPublicName() {
		return getForm().getPublicName();
	}
	
	public GameCharacter getForm() {
		return current;
	}

	@Override
	public String getBaseName() {
		return getForm().getBaseName();
	}
	
	@Override
	public String getFullName() {
		return "Changeling " + "(as " + getForm().getBaseName() + ")";
	}
	
	@Override
	public char getIcon(Player whosAsking) {
		return getForm().getIcon(whosAsking);
	}
	
	@Override
	public boolean isReduced(Actor thisActor, Actor performingClient) {
		if (getForm() instanceof ParasiteCharacter) {
			return true;
		}
		return super.isReduced(thisActor, performingClient);
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		
		if (!isAlien()) {
			addSuck(gameData, at);
		}
		
		if (!(getForms() instanceof ParasiteCharacter)) {
			addConsumeCorpse(gameData, at);
		}
	
		if (!(getForm() instanceof HorrorCharacter)) {
			addSuckAndTransform(gameData, at);
			addChangeForm(gameData, at);
			
		} else {
			addHunt(gameData, at);
		}
        addSprayAcid(gameData, at);
		getForm().addCharacterSpecificActions(gameData, at);
	}

    private void addSprayAcid(GameData gameData, ArrayList<Action> at) {
        if (getPosition().getActors().size() > 1 && !acidSprayed) {
            Action acid = new SprayAcidAction(this);
            at.add(acid);
        }

    }

    private void addHunt(GameData gameData, ArrayList<Action> at) {
		HuntAction ac = new HuntAction(getActor());
		if (ac.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
			at.add(ac);
		}
	}

	private void addConsumeCorpse(GameData gameData, ArrayList<Action> at) {
		DevourCorpseAction a = new DevourCorpseAction(this);
		if (a.roomHasHusks()) {
			at.add(a);
		}
		
	}

	private void addChangeForm(GameData gameData, ArrayList<Action> at) {
		Action a = new ChangeFormAction(this);
		if (a.getOptions(gameData, this.getActor()).numberOfSuboptions() > 1) {
			at.add(a);
		}
	}

	private boolean isAlien() {
		return getForm() instanceof ParasiteCharacter ||
				getForm() instanceof HorrorCharacter;
	}

	private void addSuck(GameData gameData, ArrayList<Action> at) {
		SuctionAttackAction suc = new SuctionAttackAction(this.getActor(), this);
		if (suc.getOptions(gameData, this.getActor()).numberOfSuboptions() > 0) {
			at.add(suc);	
		}
	}

	private void addSuckAndTransform(GameData gameData, ArrayList<Action> at) {
		SuctionAttackAction suc = new SuctionAndTransformAttackAction(this.getActor(), this);
		if (suc.getOptions(gameData, this.getActor()).numberOfSuboptions() > 0) {
			at.add(suc);	
		}
	}

	public void addToSucked(Actor other) {
		if (alreadySucked(other.getCharacter())) {
            Logger.log(Logger.INTERESTING, "Already sucked that char!" + other.getCharacter().getBaseName());
			return;
		}
		GameCharacter copy = other.getCharacter().clone();
		copy.setGender(other.getCharacter().getGender());
		while (copy.getSuit() != null) {
			copy.removeSuit();
		}
		while (copy.getItems().size() > 0) {
			copy.getItems().remove(0);
		}
	
		copy.setActor(this.getActor());
		forms.add(copy);
		if (forms.size() == 2) {
			forms.add(ultimate);
		}
	}

	private boolean alreadySucked(GameCharacter character) {
		for (GameCharacter gc : forms) {
			if (gc.getClass() == character.getClass()) {
				return true;
			}
		}
		return false;
	}

	public void changeInto(GameCharacter target) {
		for (GameCharacter gc : forms) {
			if (gc.getBaseName().equals(target.getBaseName())) {
				transferClothes(gc);
				this.current = gc;
				if (isAlien()) {
					dropAllItems();
				}
				if (current instanceof HorrorCharacter) {
					double m = 0.5 + getForms().size()*0.5;
					this.setMaxHealth(m);
					double h = this.getHealth() + getForms().size()*0.5;
					this.setHealth(Math.min(m, h));
					ultimate.setImpalerDamage(getForms());
				}
				return;
			}
		}
		throw new NoSuchInstanceException("Could not find character to change into.");
	}
	


	@Override
	public GameCharacter clone() {
		return new ChangelingCharacter(startRoom);
	}
	
	
	@Override
	public void putOnSuit(SuitItem gameItem) {
		if (!isAlien()) {
			getForm().putOnSuit(gameItem);
		}
	}
	
	@Override
	public boolean hasInventory() {
		return getForm().hasInventory();
	}
	
	@Override
	public boolean canUseObjects() {
		return getForm().canUseObjects();
	}
	
	
	@Override
	public Weapon getDefaultWeapon() {
		return getForm().getDefaultWeapon();
	}
	
	public List<GameCharacter> getForms() {
		return forms;
	}
	
	private void dropAllItems() {
		while (getItems().size() > 0) {
			this.getPosition().addItem(getItems().get(0));
			getItems().remove(0);
		}
	}

	private void transferClothes(GameCharacter gc) {
		List<SuitItem> suits = new ArrayList<>();
		while (current.getSuit() != null) {
            current.getSuit().beingTakenOff(getActor());
			suits.add(0, current.getSuit());
			current.removeSuit();
		}
		
		while (suits.size() > 0) {
			if (gc instanceof ParasiteCharacter) {
				this.getPosition().addItem(suits.get(0));
			} else if (gc instanceof HorrorCharacter) {
				this.getPosition().addItem(new TornClothes());
			} else { 
				gc.putOnSuit(suits.get(0));
			}
			suits.remove(0);
		}

		
	}

	public static boolean isDetectable(Target target2) {
		if (target2.isDead()) { 
			return false;
		}
		if (target2 instanceof GameObject) {
			return false;
		}
		
		Actor actor = (Actor)target2;

        return (actor instanceof HumanNPC ||
				actor.getCharacter().checkInstance(new InstanceChecker() {
                    @Override
                    public boolean checkInstanceOf(GameCharacter ch) {
                        return ch instanceof AnimalCharacter;
                    }
                }) ||
				actor.getCharacter().checkInstance(new InstanceChecker() {
                    @Override
                    public boolean checkInstanceOf(GameCharacter ch) {
                        return ch instanceof HumanCharacter;
                    }
                }));
	}

	@Override
	public boolean checkInstance(InstanceChecker infectChecker) {
		for (GameCharacter chara : getForms()) {
			if (infectChecker.checkInstanceOf(chara)) {
				return true;
			}
		}
		return super.checkInstance(infectChecker);
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}

    public void setAcidSprayed(boolean acidSprayed) {
        this.acidSprayed = acidSprayed;
    }
}
