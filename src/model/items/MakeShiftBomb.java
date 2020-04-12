package model.items;

import model.Actor;
import model.GameData;

public class MakeShiftBomb extends CryoBomb {
    public MakeShiftBomb(GameData gameData, Actor maker) {
        super(gameData, maker);
        this.setName("Makeshift Bomb");
    }
}
