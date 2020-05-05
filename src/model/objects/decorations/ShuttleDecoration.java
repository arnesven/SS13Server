package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.objects.general.GameObject;

import java.util.HashMap;
import java.util.Map;

public abstract class ShuttleDecoration extends GameObject {
    private final ShuttleRoom shuttle;

    public ShuttleDecoration(String name, ShuttleRoom position) {
        super(name, position);
        this.shuttle = position;
    }

    protected ShuttleRoom getShuttle() {
        return shuttle;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        Map<String, Sprite> sprs = new HashMap<>();
        sprs.put("right", getRightSprite());
        sprs.put("left", getLeftSprite());
        sprs.put("up", getUpSprite());
        sprs.put("down", getDownSprite());

        return sprs.get(getShuttle().getDirection());
    }

    protected abstract Sprite getDownSprite();

    protected abstract Sprite getUpSprite();

    protected abstract Sprite getLeftSprite();

    protected abstract Sprite getRightSprite();


    public abstract boolean alwaysShowSprite();

    public abstract void moveTo(int x, int y, int z);
}
