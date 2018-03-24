package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;
import util.Logger;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SetHairColorPower extends MovePower {
    private final Color color;
    private static List<Color> list;

    public SetHairColorPower(Color color) {
        super("Set Hair Color");
        this.color = color;
    }

    public static List<Color> getHairColors() {
        List<Color> list = new ArrayList<>();
        list.add(new Color(240, 227, 142));
        list.add(new Color(234, 208, 104));
        list.add(new Color(218, 197, 134));
        list.add(new Color(178, 146, 88));
        list.add(new Color(141, 110, 55));
        list.add(new Color(234, 135, 30));
        list.add(new Color(213, 117, 10));
        list.add(new Color(192, 98, 5));
        list.add(new Color(172, 79, 0));
        list.add(new Color(126, 58, 6));
        list.add(new Color(45, 23, 14));
        list.add(new Color(109,2,2));
	    list.add(new Color(141,6,6));
	    list.add(new Color(176,14,14));
	    list.add(new Color(214,19,19));
	    list.add(new Color(242,35,35));
        list.add(new Color(77, 45, 26));
        list.add(new Color(109, 71, 48));
        list.add(new Color(169, 126, 109));
        list.add(new Color(204, 159, 136));
        list.add(new Color(255, 255, 255));
        list.add(new Color(182, 178, 178));
        list.add(new Color(124, 124, 124));
        list.add(new Color(71, 71, 71));
        list.add(new Color(0, 0, 0));
        list.add(new Color(193, 251, 232));
        list.add(new Color(255, 218, 218));
        list.add(new Color(172, 105, 210));
        list.add(new Color(176, 244, 155));
        list.add(new Color(221, 127, 179));
        return list;
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
        player.getCharacter().getNakedness().setHairColor(color);
    }
}
