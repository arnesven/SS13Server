package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.DormsRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 10/09/17.
 */
public class Shower extends GameObject {
    public Shower(DormsRoom dormsRoom) {
        super("Shower", dormsRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {

        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("showerhead", "watercloset.png", 7, 2, this));
        list.add(new Sprite("showerwater", "watercloset.png", 7, 3, this));
        list.add(new Sprite("showersteam", "watercloset.png", 0, 5, this));

        return new Sprite("totalshower", "watercloset.png", 1, list, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new ShowerOffAction());
    }

}
