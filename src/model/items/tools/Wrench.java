package model.items.tools;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import sounds.Sound;

public class Wrench extends GameItem {
    public Wrench() {
        super("Wrench", 0.35, true, 10);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wrench", "items.png", 27, this);
    }

    @Override
    public GameItem clone() {
        return new Wrench();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good for manually opening (unpowered) doors.";
    }

    @Override
    public Sound getDropSound() {
        return new Sound("crowbar_drop");
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("crowbar_pickup");
    }
}
