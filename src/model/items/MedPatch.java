package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.MedKit;

public class MedPatch extends MedKit {

    public MedPatch() {
        setName("MedPatch");
        decrementUses();
        setCost(15);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("medpatch", "items.png", 35, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A one-time-use medical patch.";
    }
}
