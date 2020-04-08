package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class ComfyChair extends GameObject {
    public ComfyChair(Room r) {
        super("Comfortable Chair", r);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("comfychair", "furniture2.png", 3, 10, this);
    }
}
