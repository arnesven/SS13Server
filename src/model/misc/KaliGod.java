package model.misc;

import model.Actor;
import model.GameData;
import model.objects.Altar;

public class KaliGod extends ReligiousFigure {
    public KaliGod() {
        super("Kali", 10, "hindu");
    }

    @Override
    public void doWhenPrayedTo(GameData gameData, Actor prayer, Altar altar) {
        super.doWhenPrayedTo(gameData, prayer, altar);
        altar.addToPoints(-1);

    }
}
