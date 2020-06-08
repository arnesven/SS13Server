package model.movepowers;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public abstract class MovePower implements SpriteObject {
    private String name;

    public MovePower(String name) {
        this.name = name;
    }

    public abstract void activate(GameData gameData, Actor performingClient, MovementData moveData);

    public String getName() {
        return name;
    }

    public abstract Sprite getButtonSprite();

    public boolean isApplicable(GameData gameData, Actor performingClient) {return true;}

    public void gotTriggered(GameData gameData, Player player) { }


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getButtonSprite();
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return getName();
    }


    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        return new ArrayList<>();
    }

    @Override
    public boolean hasAbsolutePosition() {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
    }

    @Override
    public double getAbsoluteX() {
        return 0;
    }

    @Override
    public double getAbsoluteY() {
        return 0;
    }

    @Override
    public double getAbsoluteZ() {
        return 0;
    }

    @Override
    public String getEffectIdentifier(Actor whosAsking) {
        return getSprite(whosAsking).getName();
    }
}
