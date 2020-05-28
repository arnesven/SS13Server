package model.characters.special;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.CommitSuicideAction;
import model.actions.characteractions.HatchAction;
import model.actions.characteractions.LayEggsAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.foods.ExplodingFood;
import model.items.foods.SpaceBurger;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.map.rooms.StationRoom;
import model.npcs.NPC;
import model.objects.AlienEggObject;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlienCharacter extends GameCharacter {

    public static final int STAGE_EGG = 0;
    public static final int STAGE_PARASITE = 1;
    public static final int STAGE_ADULT = 2;
    private static Set<Integer> usedIDs = new HashSet<>();
    private final Room startRoom;
    private int stage;
    private int ageInTurns;
    private int eggs;

    public AlienCharacter(Room randomStartRoom) {
        super("Alien #" + getRandomUID(), randomStartRoom.getID(), 6.6);
        this.startRoom = randomStartRoom;
        this.stage = STAGE_EGG;
        setHealth(1.0);
        setMaxHealth(1.0);
        ageInTurns = 0;
        eggs = 0;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (stage == STAGE_EGG) {
            return new Sprite("alieneggchar", "alien2.png", 1, 8, getActor());
        } else if (stage == STAGE_PARASITE) {
            ParasiteCharacter pchar = new ParasiteCharacter();
            if (isDead()) {
                pchar.setHealth(0.0);
            }
            Sprite sp = pchar.getSprite(whosAsking);
            sp.setObjectRef(getActor());
            return sp;
        }

        if (isDead()) {
            return new Sprite("alienadultdead", "alien2.png", 4, 2, getActor());
        }

        if (ageInTurns >= 24) {
            return new Sprite("alienadultqueen", "alien2.png", 0, 19, 64, 32, getActor());
        }else if (ageInTurns >= 16) {
            return new Sprite("alienadultolder", "alien2.png", 5, 4, getActor());
        } else if (ageInTurns >= 8) {
            return new Sprite("alienadultold", "alien2.png", 10, 2, getActor());
        }
        return new Sprite("alienadultyoung", "alien2.png", 0, 2, getActor());
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (stage == STAGE_EGG) {
            return "Alien Egg";
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public int getMovementSteps() {
        if (stage == STAGE_EGG) {
            return 0;
        }
        return super.getMovementSteps();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (stage == STAGE_EGG) {
            at.add(new HatchAction());
        } else if (eggs > 0 && getPosition() instanceof StationRoom) {
            at.add(new LayEggsAction(this));
        }

    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public boolean getsObjectActions() {
        if (stage == STAGE_EGG) {
            return false;
        }
        return super.getsObjectActions();
    }

    @Override
    public boolean getsRoomActions() {
        if (stage == STAGE_EGG) {
            return false;
        }
        return super.getsRoomActions();
    }

    @Override
    public boolean getsTargetingActions() {
        if (stage == STAGE_EGG) {
            return false;
        }
        return super.getsTargetingActions();
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> it = new ArrayList<>();
        return it;
    }

    @Override
    public GameCharacter clone() {
        return new AlienCharacter(startRoom);
    }

    private static int getRandomUID() {
        int uid;
        do {
            uid = MyRandom.nextInt(999);
        } while (usedIDs.contains(uid));

        return uid;
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof RadiationDamage ||
                damager instanceof ColdDamage ||
                damager instanceof AsphyxiationDamage) {
            return;
        }
        super.beExposedTo(something, damager);
    }

    @Override
    public int getSize() {
        if (stage == STAGE_PARASITE) {
            return SMALL_SIZE;
        }
        return super.getSize();
    }

    @Override
    public Weapon getDefaultWeapon() {
        if (stage == STAGE_PARASITE) {
            return Weapon.CLAWS;
        }
        return Weapon.HUGE_CLAWS;
    }

    public void setStage(int newStage) {
        this.stage = newStage;
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        if (stage != STAGE_EGG) {
            ageInTurns++;
            if (getActor() instanceof NPC && ageInTurns > 10) {
                ageInTurns = 10;
            }
        }
        if (ageInTurns == 4) {
            getActor().addTolastTurnInfo(HTMLText.makeText("Green", "<b>You grow stronger.</b>"));
            setMaxHealth(2.0);
            setHealth(2.0);
            this.stage = STAGE_ADULT;
        } else if (ageInTurns == 6) {
            getActor().addTolastTurnInfo(HTMLText.makeText("Green", "<b>You have matured and can now lay eggs.</b>"));
            eggs = 1;
        } else if (ageInTurns % 8 == 0) {
            getActor().addTolastTurnInfo(HTMLText.makeText("Green", "<b>You grow stronger.</b>"));
            setMaxHealth(getMaxHealth()+1.0);
            setHealth(getHealth()+1.0);
        }

        if (gameData.getRound() % 3 == 0 && ageInTurns >= 6) {
            eggs++;
        }
    }

    public void layEggsInRoom(Actor performingClient, Room position, GameData gameData) {
        AlienEggObject eggObj = new AlienEggObject(Math.min(eggs, 3), position);
        position.addObject(eggObj);
        eggs = Math.max(0, eggs - 3);
        performingClient.addTolastTurnInfo("You laid " + eggObj.getNumber() + " egg(s) in " + position.getName());
        gameData.addEvent(eggObj.createEvent());
    }

    public int getAge() {
        return ageInTurns;
    }
}
