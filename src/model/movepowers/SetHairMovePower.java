package model.movepowers;

import graphics.sprites.NakedHumanSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;
import util.Logger;
import util.MyRandom;

import java.awt.*;

public class SetHairMovePower extends MovePower {
    private final int target;

    public SetHairMovePower(int target) {
        super("Set Hair");
        this.target = target;
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {

    }

    @Override
    public Sprite getButtonSprite() {
        return NakedHumanSprite.getHairSprite(target);
    }

    @Override
    public void gotTriggered(GameData gameData, Player player) {
        Logger.log("Changed hair for player!");
        player.getCharacter().setNakedSprite(new NakedHumanSprite(player.getCharacter().getGender().equals("man"), target,
                player.getSettings().getSelectedHairColor()));
    }
}
