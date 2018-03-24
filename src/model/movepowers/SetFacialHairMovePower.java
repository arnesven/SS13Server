package model.movepowers;

import graphics.sprites.Nakedness;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;

public class SetFacialHairMovePower extends MovePower {
    private final int target;

    public SetFacialHairMovePower(int i) {
        super("Set Facial Hair");
        this.target = i;
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {

    }

    @Override
    public Sprite getButtonSprite() {
        return Nakedness.getFacialHairSprite(target);
    }

    @Override
    public void gotTriggered(GameData gameData, Player player) {
        player.getCharacter().getNakedness().setFacialHairNumber(target);
    }
}
