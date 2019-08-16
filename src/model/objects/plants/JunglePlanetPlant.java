package model.objects.plants;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.HideableObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 16/09/17.
 */
public class JunglePlanetPlant extends HideableObject {
    public JunglePlanetPlant(Room room) {
        super("Jungle Tree", 1, room);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("jungleplant", "jungle.png", 16, 7, this);
    }
}
