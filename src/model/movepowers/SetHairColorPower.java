package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;
import util.Logger;

import java.awt.*;

public class SetHairColorPower extends MovePower {
    private final Color color;

    public SetHairColorPower(Color color) {
        super("Set Hair Color");
        this.color = color;
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {

    }

    @Override
    public Sprite getButtonSprite() {
        Sprite sp = new Sprite("colorblob"+color.getRed()+"x"+color.getGreen()+"x"+color.getBlue(), "human_face.png", 0);
        sp.setColor(color);
        return sp;
    }

    @Override
    public void gotTriggered(GameData gameData, Player player) {
        Logger.log("Set hair color!");
        player.getSettings().setSelectedHairColor(color);
    }
}
