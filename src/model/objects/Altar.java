package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.MakeRitualSacrifice;
import model.actions.objectactions.PrayerAction;
import model.map.rooms.ChapelRoom;
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
        gods.add(new BabyJesus());
        gods.add(new ReligiousFigure("Moses", 6));
        gods.add(new ReligiousFigure("Allah", 7));
        gods.add(new KaliGod());
        gods.add(new Satan());
        gods.add(new ReligiousFigure("L. Ron Hubbard", 8));

        pedestals = new ArrayList<>();
        pedestals.add(new Pedestals("Left", room));
        pedestals.add(new Pedestals("Right", room));
        for (Pedestals p : pedestals) {
            room.addObject(p);
        }

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
        if (lastPrayedTo instanceof KaliGod) { // TODO: is this too powerful, maybe one needs a ritual dagger...
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
