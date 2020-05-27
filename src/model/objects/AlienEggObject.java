package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class AlienEggObject extends BreakableObject {

    private final int number;

    public AlienEggObject(int number, Room position) {
        super("Alien Egg", number, position);
        this.number = number;
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("alieneggobj", "alien2.png", 1, 8, this);
    }

    public int getNumber() {
        return number;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }
}
