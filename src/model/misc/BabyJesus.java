package model.misc;

import model.Actor;
import model.GameData;
import model.objects.Altar;

public class BabyJesus extends ReligiousFigure {
    public BabyJesus() {
        super("Baby Jesus", 5, "catholic");
    }

    @Override
    public void doWhenPrayedTo(GameData gameData, Actor prayer, Altar altar) {
        super.doWhenPrayedTo(gameData, prayer, altar);
        altar.setChristian(true);
    }
}
