package model.map.rooms;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class RoomPosters implements Serializable {

    public RoomPosters() {
        new Sprite("poster1left", "posters.png", 0, null);
        new Sprite("poster1right", "posters.png", 1, null);
        new Sprite("walldarktop", "walls.png", 12, 2, null);
        new Sprite("walldarkside", "walls.png", 3, 2, null);
        new Sprite("walldarkcorner", "walls.png", 6, 2, null);
        new Sprite("walldarkLL", "walls.png", 1, 2, null);
        new Sprite("walldarkUR", "walls.png", 10, 2, null);
        new Sprite("normaldoor", "doors.png", 10, 19, null);
        new Sprite("lockeddoor", "doors.png", 11, 19, null);
        new Sprite("walldarkwindowleft", "walls.png", 5, 20, null);
        new Sprite("walldarkwindowright", "walls.png", 6, 20, null);
        new Sprite("walldarkwindowtop", "walls.png", 7, 20, null);
        new Sprite("walldarkwindowbottom", "walls.png", 8, 20, null);
    }
}
