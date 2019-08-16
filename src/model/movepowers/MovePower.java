package model.movepowers;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;

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
}
