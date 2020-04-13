package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.JanitorialRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

public class PosterObject extends GameObject  {
    private final int col;
    private final int row;
    private final double wallPos;
    private final String name;

    public PosterObject(Room room, String name, int column, int row, double wallPosition) {
        super(name + " Poster", room);
        this.col = column;
        this.row = row;
        this.wallPos = wallPosition;
        this.setAbsolutePosition(room.getX() + wallPos, room.getY());
        this.name = name;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite(name.toLowerCase().replaceAll(" ", "_") + "poster",
                "posters.png", col, row, this);
    }
}
