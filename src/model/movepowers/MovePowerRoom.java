package model.movepowers;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.RoomType;

import java.util.ArrayList;
import java.util.List;

public class MovePowerRoom extends Room {
    private final MovePower movePower;

    public MovePowerRoom(int id, String name, int x, int y, MovePower mp, int width) {
        super(id, name, "", x, y, width, 1, new int[0], new double[0], RoomType.button);
        this.movePower = mp;
    }

    public MovePower getMovePower() {
        return movePower;
    }

    @Override
    public List<Sprite> getAlwaysSprites() {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(getButtonSprite());
        return sprs;
    }

    public Sprite getButtonSprite() {
        return this.movePower.getButtonSprite();
    }

    public void gotTriggered(GameData gameData, Player player) {
        movePower.gotTriggered(gameData, player);
    }
}