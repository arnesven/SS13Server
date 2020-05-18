package model.objects.power;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ShowInspectObjectFancyFrame;
import model.map.rooms.KitchenRoom;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class AreaPowerControl extends GameObject {
    private final Room room1;
    private final Room room2;

    public AreaPowerControl(Room room1, Room room2) {
        super("APC - " + room1.getName()+"/"+room2.getName(), room1);
        room2.addObject(this);
        this.room1 = room1;
        this.room2 = room2;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("apc", "power.png", 6, 10, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (cl instanceof Player) {
            at.add(new ShowInspectObjectFancyFrame((Player)cl, gameData, this));
        }
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }
}
