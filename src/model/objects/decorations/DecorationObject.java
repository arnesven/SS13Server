package model.objects.decorations;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;

public class DecorationObject extends GameObject {
    public DecorationObject(String name, Room position) {
        super(name, position);
    }

}
