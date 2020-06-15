package model.objects;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.BridgeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class BridgeChair extends GameObject {
    public BridgeChair(String whos, Room room) {
        super(whos + "'s Seat", room);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("bridgechair", "chairs.png", 4, 10, this);
    }
}
