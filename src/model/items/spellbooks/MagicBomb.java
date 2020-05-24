package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.items.MakeShiftBomb;
import model.items.general.GameItem;

public class MagicBomb extends MakeShiftBomb {
    public MagicBomb(GameData gameData, Actor performingClient) {
        super(gameData, performingClient);
        setName("Magic Bomb");
    }
}
