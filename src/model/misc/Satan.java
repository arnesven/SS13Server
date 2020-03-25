package model.misc;

import model.Actor;
import model.GameData;
import model.objects.Altar;
import model.objects.Pedestals;

public class Satan extends ReligiousFigure {
    private boolean firstTime = true;

    public Satan() {
        super("Satan", 9);
    }

    @Override
    public void doWhenPrayedTo(GameData gameData, Actor prayer, Altar altar) {
        super.doWhenPrayedTo(gameData, prayer, altar);
        if (firstTime) {
            altar.addToPoints(-11);
            for (Pedestals p : altar.getPedestals()) {
                p.setBurning(true);
            }
        } else {
            altar.addToPoints(-2);
        }
        firstTime = false;
    }
}
