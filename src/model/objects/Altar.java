package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PrayerAction;
import model.map.rooms.ChapelRoom;
import model.map.rooms.Room;
import model.misc.ReligiousFigure;
import model.misc.Satan;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Altar extends GameObject {
    private final List<ReligiousFigure> gods;
    private int points = 0;
    private final List<Pedestals> pedestals;

    public Altar(Room room) {
        super("Altar", room);
        this.gods = new ArrayList<ReligiousFigure>();
        gods.add(new ReligiousFigure("Baby Jesus", 5));
        gods.add(new ReligiousFigure("Moses", 6));
        gods.add(new ReligiousFigure("Allah", 7));
        gods.add(new Satan());
        gods.add(new ReligiousFigure("L. Ron Hubbard", 8));

        pedestals = new ArrayList<>();
        pedestals.add(new Pedestals(room));
        pedestals.add(new Pedestals(room));
        for (Pedestals p : pedestals) {
            room.addObject(p);
        }

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("altar", "altars.png", 9, 0, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new PrayerAction(this));
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
}
