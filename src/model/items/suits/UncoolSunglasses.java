package model.items.suits;

import model.Actor;
import model.GameData;
import model.Player;

/**
 * Created by erini02 on 17/11/16.
 */
public class UncoolSunglasses extends SunGlasses {

    @Override
    public void beingPutOn(Actor actionPerformer) {

    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {

    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A pair of unstylish sunglasses. These aren't cool at all!";
    }
}
