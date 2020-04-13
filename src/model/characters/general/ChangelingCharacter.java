package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.characteractions.*;
import model.actions.general.Action;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.NoSuchInstanceException;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.general.TornClothes;
import model.items.suits.SuitItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.npcs.HumanNPC;
import model.objects.general.GameObject;
import util.Logger;

public class ChangelingCharacter extends GameCharacter {
	
	private List<GameCharacter> forms = new ArrayList<>();
	private GameCharacter current;
	private Room startRoom;
	private HorrorCharacter ultimate = new HorrorCharacter();
    private boolean acidSprayed = false;
    private int extra = 0;

    public ChangelingCharacter(Room startRoom) {
		super("Changeling", startRoom.getID(), 21.0);
		this.startRoom = startRoom;
		setMaxHealth(2.0);
		setHealth(2.0);
		current = new ParasiteCharacter();
		forms.add(current);
	}

	public static String getAntagonistDescription() {
		return "<font size=\"3\"><i>You are an alien with shape-shifting powers, " +
				"hell-bent on killing the crew of SS13! You start as a parasite-like " +
				"creature but as you suck the life out of other creatures you can take " +
				"their shape. You also have an Ultimate form which is very powerful. </i><br/>"+
				"<b>Abilities: </b> Change Form, Hunting, Spray Acid<br/>" +
				"<b>Initiative</b> 21.0<br/>" +
				"</font>";
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
    public Sprite getSprite(Actor whosAsking) {
        return getForm().getSprite(whosAsking);
    }

	@Override
	public int getSize() {
		return getForm().getSize();
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
        Logger.log("Adding specific actions for form");
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
		copy.setNakedness(copy.getPhysicalBody());
		copy.getEquipment().removeEverything();

		while (copy.getItems().size() > 0) {
			copy.getItems().remove(0);
		}
	
		copy.setActor(this.getActor());
		forms.add(copy);
		if (forms.size() == 2) {
            ultimate.setActor(this.getActor());
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
					double m = 0.5 + getPower();
					this.setMaxHealth(m);
					double h = this.getHealth() + getPower();
					this.setHealth(Math.min(m, h));
					ultimate.setImpalerDamage(getPower() - 0.5);
                    ((HorrorCharacter)current).setChangeling(this);
				}
				return;
			}
		}
		throw new NoSuchInstanceException("Could not find character to change into.");
	}


    @Override
    public void beExposedTo(Actor something, Damager damager) {
        getForm().beExposedTo(something, damager);
    }

    @Override
	public GameCharacter clone() {
		return new ChangelingCharacter(startRoom);
	}


	@Override
	public void putOnEquipment(SuitItem eqItem) {
    	if (!isAlien()) {
			super.putOnEquipment(eqItem);
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
		List<SuitItem> suits = current.getEquipment().getSuitsAsList();
		current.getEquipment().removeEverything();

		
		while (suits.size() > 0) {
			if (gc instanceof ParasiteCharacter) {
				this.getPosition().addItem(suits.get(0));
			} else if (gc instanceof HorrorCharacter) {
				this.getPosition().addItem(new TornClothes());
			} else { 
				gc.putOnEquipment(suits.get(0));
			}
			suits.remove(0);
		}

		
	}

	public static boolean isDetectable(Target target2) {
		if (!target2.isTargetable()) {
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
//		for (GameCharacter chara : getForms()) {
//			if (infectChecker.checkInstanceOf(chara)) {
//				return true;
//			}
//		}
        return getForm().checkInstance(infectChecker);
//        return super.checkInstance(infectChecker);
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}

    public void setAcidSprayed(boolean acidSprayed) {
        this.acidSprayed = acidSprayed;
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        if (getHealth() < getMaxHealth() && !isDead()) {
            getActor().addTolastTurnInfo("You regenerated some health.");
            setHealth(getHealth() + 0.5);
        }

    }

    public double getPower() {
        return (getForms().size() + extra) * 0.5 ;
    }

    public void addToPower(int i) {
        extra += i;
    }

	@Override
	public Sprite getMugshotSprite(GameData gameData, Actor player) {
		return getForm().getMugshotSprite(gameData, player);
	}

	@Override
	public String getMugshotName() {
		return "Changeling";
	}

	@Override
	public String getRadioName() {
    	if (current instanceof HorrorCharacter) {
    		return "Something";
		}
		return super.getRadioName();
	}
}
