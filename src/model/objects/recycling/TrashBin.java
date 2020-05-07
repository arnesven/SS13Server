package model.objects.recycling;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.RecycleAction;
import model.map.rooms.StationRoom;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class TrashBin extends GameObject {
    public TrashBin(StationRoom stationRoom) {
        super("Trash Bin", stationRoom);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("trashbin", "storage.png", 51, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        at.add(new RecycleAction());
    }
}
