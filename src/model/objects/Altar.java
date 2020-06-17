package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.MakeRitualSacrifice;
import model.actions.objectactions.PrayerAction;
import model.map.rooms.ChapelRoom;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;
import model.misc.BabyJesus;
import model.misc.KaliGod;
import model.misc.ReligiousFigure;
import model.misc.Satan;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Altar extends GameObject {
    private final List<ReligiousFigure> gods;
    private int points = 0;
    private final List<Pedestals> pedestals;
    private boolean isChristian = true;
    private ReligiousFigure lastPrayedTo = null;
    private boolean isSacrifice = false;

    public Altar(Room room) {
        super("Altar", room);
        this.gods = new ArrayList<ReligiousFigure>();

        for (ReligiousFigure r : getReligiousFigures()) {
            gods.add(r);
        }


        pedestals = new ArrayList<>();
        pedestals.add(new Pedestals("Left", room));
        pedestals.add(new Pedestals("Right", room));
        room.addObject(pedestals.get(0), RelativePositions.MID_TOP);
        room.addObject(pedestals.get(1), RelativePositions.MID_BOTTOM);

    }

    public static List<ReligiousFigure> getReligiousFigures() {
        List<ReligiousFigure> gods = new ArrayList<>();
        gods.add(new BabyJesus());
        gods.add(new ReligiousFigure("Moses", 6, "jew"));
        gods.add(new ReligiousFigure("Allah", 7, "muslim"));
        gods.add(new KaliGod());
        gods.add(new Satan());
        gods.add(new ReligiousFigure("L. Ron Hubbard", 8, "scientologist"));
        return gods;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isChristian) {
            return new Sprite("altarchristian", "altars.png", 9, 0, this);
        } else if (isSacrifice) {
            return new Sprite("altarsacrifice", "altars.png", 12, 0, this);
        }
        return new Sprite("altarnormal", "altars.png", 8, 0, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new PrayerAction(this));
        if (lastPrayedTo instanceof KaliGod) {
            at.add(new MakeRitualSacrifice(this));
        }
    }

    public List<ReligiousFigure> getGods() {
        return gods;
    }

    public int getPoints() {
        return points;
    }

    public void addToPoints(int x) {
        points += x;
    }

    public List<Pedestals> getPedestals() {
        return pedestals;
    }

    public void setChristian(boolean b) {
        isChristian = b;
    }

    public void setSacrifice(boolean sacrifice) {
        isSacrifice = sacrifice;
    }

    public void setLastPrayed(ReligiousFigure lastPrayed) {
        this.lastPrayedTo = lastPrayed;
    }
}
