package model.movepowers;

import graphics.sprites.PhysicalBody;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;
import util.Logger;

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
        return PhysicalBody.getHairSprite(target);
    }

    @Override
    public void gotTriggered(GameData gameData, Player player) {
        Logger.log("Changed hair for player!");
        player.getCharacter().getPhysicalBody().setHairNumber(target);
    }
}
