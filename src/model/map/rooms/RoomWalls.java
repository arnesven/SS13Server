package model.map.rooms;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class RoomWalls implements Serializable {

    public RoomWalls() {
        new Sprite("poster1left", "posters.png", 0, null);
        new Sprite("poster1right", "posters.png", 1, null);
        new Sprite("walldarktop", "walls.png", 12, 2, null);
        new Sprite("walldarkside", "walls.png", 3, 2, null);
        new Sprite("walldarkcorner", "walls.png", 6, 2, null);
        new Sprite("walldarkLL", "walls.png", 5, 2, null);
        new Sprite("walldarkLR", "walls.png", 9, 2, null);
        new Sprite("walldarkUR", "walls.png", 10, 2, null);
        new Sprite("walldarkwindowleft", "walls.png", 5, 20, null);
        new Sprite("walldarkwindowright", "walls.png", 6, 20, null);
        new Sprite("walldarkwindowtop", "walls.png", 7, 20, null);
        new Sprite("walldarkwindowbottom", "walls.png", 8, 20, null);
        new Sprite("walldarkwindowup", "walls.png", 9, 20, null);
        new Sprite("walldarkwindowdown", "walls.png", 10, 20, null);
        new Sprite("walldarkwindowlinks", "walls.png", 11, 20, null);
        new Sprite("walldarkwindowrechts", "walls.png", 12, 20, null);
    }
}
