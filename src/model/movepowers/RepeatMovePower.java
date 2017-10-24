package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.Player;

public class RepeatMovePower extends MovePower {
    public RepeatMovePower() {
        super("Repeat Last Action");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        if (performingClient instanceof Player) {
            if (((Player) performingClient).getNextAction() != null) {
                ((Player) performingClient).getNextAction().doTheAction(gameData, performingClient);
            }
        }
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("repeatbutton", "buttons1.png", 1);
    }
}
