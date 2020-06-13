package model.characters.special;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.FreeAction;
import model.actions.characteractions.CommitSuicideAction;
import model.actions.characteractions.HatchAction;
import model.actions.characteractions.LayEggsAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.AttackAction;
import model.characters.crew.JobDescriptionMaker;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.foods.ExplodingFood;
import model.items.foods.SpaceBurger;
import model.items.general.GameItem;
import model.items.weapons.PiercingWeapon;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.map.rooms.StationRoom;
import model.npcs.NPC;
import model.objects.AlienEggObject;
import sounds.AlienSoundSet;
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
    private boolean tailWhipReady;

    public AlienCharacter(Room randomStartRoom) {
        super("Alien #" + getRandomUID(), randomStartRoom.getID(), 6.6);
        setSoundSet(new AlienSoundSet(this));
        this.startRoom = randomStartRoom;
        this.stage = STAGE_EGG;
        setHealth(1.0);
        setMaxHealth(1.0);
        ageInTurns = 0;
        eggs = 0;
        tailWhipReady = true;
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
        if (getAge() > 7 && tailWhipReady && getActor() instanceof Player) {
            at.add(new TailWhipAction(this, gameData, (Player)getActor()));
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

    public int getStage() { return stage; }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        if (stage != STAGE_EGG) {
            ageInTurns++;
            if (getActor() instanceof NPC && ageInTurns > 10) {
                ageInTurns = 10;
            }
            if (ageInTurns % 2 == 0) {
                tailWhipReady = true;
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


    public static String getAntagonistDescription() {
        return "<font size=\"3\"><i>A malicious egg-laying xenomorph whose sole purpose seems" +
                " to be killing unsuspecting crew members.</i><br/>"+
                "<b>Abilities: </b> Evolving, Egg-laying<br/>" +
                "<b>Initiative</b> 6.6<br/>" +
                "</font>";
    }

    @Override
    public String getMugshotName() {
        return "Alien";
    }

    private class TailWhipAction extends FreeAction {
        private final AlienCharacter alienChar;
        private String preferredTarget;

        public TailWhipAction(AlienCharacter alienCharacter, GameData gameData, Player player) {
            super("Tail Whip", gameData, player);
            this.alienChar = alienCharacter;
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            for (Target t : whosAsking.getPosition().getTargets(gameData)) {
                opts.addOption(t.getName());
            }
            return opts;
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            gameData.executeAtEndOfRound(p, this);
            alienChar.tailWhipReady = false;
            gameData.getChat().serverInSay("You are preparing to hit "
                    + preferredTarget + " with your Tail Spike.", p);
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            preferredTarget = args.get(0);
            super.setArguments(args, performingClient);
        }

        @Override
        public void lateExecution(GameData gameData, Actor performingClient) {
            super.lateExecution(gameData, performingClient);
            AttackAction atk = new AttackAction(performingClient);
            atk.getTargets().remove(alienChar.getActor().getAsTarget());
            TailSpike spike = new TailSpike();
            atk.addWithWhat(spike);
            List<String> args = new ArrayList<>();
            args.add(preferredTarget);
            args.add(spike.getFullName(performingClient));
            atk.setActionTreeArguments(args, performingClient);
            alienChar.getItems().add(spike);
            atk.doTheAction(gameData, performingClient);
            alienChar.getItems().remove(spike);
        }

        @Override
        public Sprite getAbilitySprite() {
            return new Sprite("tailwhipabi", "interface_alien.png", 0, 8, null);
        }
    }

    private class TailSpike extends Weapon implements PiercingWeapon {
        public TailSpike() {
            super("Tail Spike", 0.9, 1.0, false, 0.0, false, 0);
        }

        @Override
        public GameItem clone() {
            return new TailSpike();
        }
    }
}
