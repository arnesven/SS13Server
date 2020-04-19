package model.map.doors;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.roomactions.LockDoorAction;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import util.MyStrings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Door implements Serializable, SpriteObject {

    private final Sprite sprite = getSprite();
    private final int fromID;
    private final int toID;

    private double x;
    private double y;
    private String name;

    public Door(double x, double y, String name, int fromID, int toID) {
        this.x = x;
        this.y = y;
        this.fromID = fromID;
        this.toID = toID;
        this.name = name + " Door " + getNumber();
    }

    public String getNumber() {
        return "#" + (fromID*100 + toID);
    }

    protected abstract Sprite getSprite();

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + name + ", " + getSprite().getName();
    }

    private String getActionData(GameData gameData, Actor forWhom) {
        if (forWhom.getCharacter() == null) {
            return "NoRef";
        }
        return Action.makeActionListStringSpecOptions(gameData, getDoorActions(gameData, forWhom), (Player)forWhom);
    }

    public List<Action> getDoorActions(GameData gameData, Actor forWhom) {
        List<Action> at = new ArrayList<>();
        return at;
    }

    public String getStringRepresentation(GameData gameData, Player forWhom) {
        return toString() + ", " + getActionData(gameData, forWhom);
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
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y) {
        //TODO
    }

    @Override
    public double getAbsoluteX(ClientInfo clientInfo) {
        return 0;
    }

    @Override
    public double getAbsoluteY(ClientInfo clientInfo) {
        return 0;
    }

    public int getToId() {
        return toID;
    }

    public int getFromId() {
        return fromID;
    }

    public boolean requiresPower() {
        return false;
    }

    public ElectricalMachinery getElectricalLock(GameData gameData) {
        return null;
    }
}
