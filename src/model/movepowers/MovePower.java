package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;

public abstract class MovePower {
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
}
