package model.movepowers;

import graphics.sprites.Nakedness;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;

public class SetNakedHumanPower extends MovePower {
    private final int target;

    public SetNakedHumanPower(int i) {
        super("Set naked look");
        this.target = i;
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {

    }

    @Override
    public Sprite getButtonSprite() {
        return Nakedness.getNakedSprites().get(target);
    }

    @Override
    public void gotTriggered(GameData gameData, Player player) {
        player.getCharacter().getNakedness().setNakedSpriteNum(target);
    }
}
