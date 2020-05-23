package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.DoorMechanism;
import model.map.doors.ElectricalDoor;
import model.map.doors.MagicWallDoor;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;

import java.util.ArrayList;
import java.util.List;

public class SummonWallSpellBook extends SpellBook {
    private Door targetedDoor;

    public SummonWallSpellBook() {
        super("Summon Wall", 18);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("summonwallspellbook", "wizardstuff.png", 3, 2, this);
    }

    @Override
    public boolean canBeQuickCast() {
        return false;
    }

    @Override
    public void doEarlyEffect(GameData gameData, Actor performingClient, Target target) {

    }

    @Override
    public void doLateEffect(GameData gameData, Actor performingClient, Target target) {
            performingClient.addTolastTurnInfo("You covered door " + targetedDoor.getName() + " with a wall!");
            for (int i = 0; i < performingClient.getPosition().getDoors().length; ++i) {
                if (performingClient.getPosition().getDoors()[i] == targetedDoor) {
                    try {
                        GameMap.separateRooms(gameData.getRoomForId(targetedDoor.getFromId()),
                                gameData.getRoomForId(targetedDoor.getToId()));
                        performingClient.getPosition().getDoors()[i] = new MagicWallDoor(targetedDoor);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    @Override
    public String getSpellName() {
        return "Summon Wall";
    }

    @Override
    public String getMagicWords() {
        return "Olaté Ascenderae!";
    }

    @Override
    protected CastSpellAction getCastAction(GameData gameData, Actor forWhom) {
        return new CastSummonWallSpell(this, forWhom, gameData);
    }

    @Override
    protected String getSpellDescription() {
        return "Summons a stone wall to cover up a doorway.";
    }

    @Override
    public GameItem clone() {
        return new SummonWallSpellBook();
    }

    private class CastSummonWallSpell extends CastSpellAction {
        private List<Door> doors;

        public CastSummonWallSpell(SummonWallSpellBook summonWallSpellBook, Actor forWhom, GameData gameData) {
            super(summonWallSpellBook, forWhom);
            doors = new ArrayList<>();
            for (Room r : gameData.getMap().getAllRoomsOnSameLevel(forWhom.getPosition())) {
                for (Door d : r.getDoors()) {
                    if (d.getToId() == forWhom.getPosition().getID() || d.getFromId() == forWhom.getPosition().getID()) {
                        doors.add(d);
                    }
                }
            }
        }

        @Override
        public boolean isOkToCast(Actor forWhom, GameData gameData) {
            return !doors.isEmpty();
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            opts.getSuboptions().clear();
            for (Door d : doors) {
                opts.addOption(d.getName());
            }
            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            applyTargetingAction(gameData, performingClient, null, null);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (Door d : doors) {
                if (args.get(0).equals(d.getName())) {
                    SummonWallSpellBook.this.targetedDoor = d;
                    break;
                }
            }
        }

        @Override
        protected boolean canBeTargetedBySpell(Target target2) {
            return target2 instanceof Door;
        }

    }
}
