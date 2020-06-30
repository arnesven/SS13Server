package model.map.doors;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.ActionGroup;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Door implements Serializable, SpriteObject {

    private final int fromID;
    private int toID;

    private double x;
    private double y;
    private double z;
    private String name;

    private boolean isAnimating;

    public Door(double x, double y, double z, String name, int fromID, int toID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fromID = fromID;
        this.toID = toID;
        setName(name);
        this.name = name + " Door " + getNumber();
    }

    public Door(double x, double y, String name, int fromID, int toID) {
        this(x, y, 0.0, name, fromID, toID);
    }

    public void setName(String name) {
        this.name = name + " Door " + getNumber();
    }

    public String getNumber() {
        return "#" + (fromID*1000 + toID);
    }

    protected abstract Sprite getSprite();

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEffectIdentifier(Actor whosAsking) {
        return name;
    }

    private String getActionData(GameData gameData, Actor forWhom) {
        if (forWhom.getCharacter() == null) {
            return "NoRef";
        }
        return Action.makeActionListStringSpecOptions(gameData, getNearbyDoorActions(gameData, forWhom), (Player)forWhom);
    }

    public List<Action> getNearbyDoorActions(GameData gameData, Actor forWhom) {
        if (forWhom.getCharacter().getsObjectActions()) {
            if (getToId() == forWhom.getPosition().getID() || getFromId() == forWhom.getPosition().getID() ||
                    forWhom.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
                List<Action> actions = new ArrayList<>();
                ActionGroup ag = new ActionGroup(this.getName());
                List<Action> acts = getDoorActions(gameData, forWhom);
                if (acts.size() > 0) {
                    ag.addAll(acts);
                }
                actions.add(ag);
                return actions;
            }
        }
        return new ArrayList<>();
    }

    protected List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = new ArrayList<>();
        return at;
    }

    public String getStringRepresentation(GameData gameData, Player forWhom) {
        Sprite sp = getSprite();
        if (!isVisibleFor(gameData, forWhom)) {
            sp = getFogOfWarSprite();
        }

        return x + ", " + y + ", " + z + ", " + name + ", " + sp.getName() + ", " + getActionData(gameData, forWhom);
    }

    protected Sprite getFogOfWarSprite() {
        return NormalDoor.UNPOWERED_DOOR;
    }

    private boolean isVisibleFor(GameData gameData, Player forWhom) {
        if (forWhom.getCharacter() == null) {
            return false;
        }

        if (forWhom.isAI()) {
            return true;
        }

        Room to = null;
        try {
            to = gameData.getRoomForId(toID);
            Room from = gameData.getRoomForId(fromID);
            //List<Room> nearby = forWhom.getPosition().getNeighborList();
            //return nearby.contains(to) || nearby.contains(from) ||
            //        to == forWhom.getPosition() || from == forWhom.getPosition();
            return       to == forWhom.getPosition() || from == forWhom.getPosition();

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getSprite();
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return getName() + " door";
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean hasAbsolutePosition() {
        return true;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
    }

    @Override
    public double getAbsoluteX() {
        return getX();
    }

    @Override
    public double getAbsoluteY() {
        return getY();
    }

    @Override
    public double getAbsoluteZ() {
        return getZ();
    }

    public int getToId() {
        return toID;
    }

    public void setToId(int newToID) { toID = newToID;}

    public int getFromId() {
        return fromID;
    }

    public boolean requiresPower() {
        return false;
    }

    public ElectricalMachinery getElectricalLock(GameData gameData) {
        return null;
    }

    public void setIsAnimating(boolean b) {
        isAnimating = b;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public Sprite getFireDoorOpenAnimatedSprite() {
        List<Sprite> sps = new ArrayList<>();
        sps.add(new Sprite("doorblank", "blank.png", 0,null));
        sps.add(getSprite());
        sps.add(new AnimatedSprite("openingfiredoor", "doors.png",
                14, 9, 32, 32, null, 7, false));
        return new AnimatedSprite("openingfiredoorani", sps, 7, false);
    }

    public MoveAction makeMoveThroughDoorAction(GameData gameData, Actor performingClient) {
        try {
            int destinationRoom = getToId();
            Room to = gameData.getRoomForId(getToId());
            if (to == performingClient.getPosition()) {
                destinationRoom = getFromId();
            }
            MoveAction ma = new MoveAction(gameData, performingClient);
            List<String> args = new ArrayList<>();
            args.add(gameData.getRoomForId(destinationRoom).getName());
            ma.setArguments(args, performingClient);
            return ma;
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
        return null;
    }
}
