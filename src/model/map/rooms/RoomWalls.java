package model.map.rooms;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class RoomWalls implements Serializable {

    private WallSet darkSet;
    private WallSet shuttleSet;

    public RoomWalls() {
        darkSet = new DarkWallSet("dark");
        shuttleSet = new ShuttleWallSet("shuttle");


    }



    private class WallSet {
        private final String name;
        public WallSet(String name) {
            this.name = name;
        }
    }

    private class DarkWallSet extends WallSet {
        public DarkWallSet(String dark) {
            super(dark);
            new Sprite("walldarktop", "walls.png", 12, 2, null);
            new Sprite("walldarkside", "walls.png", 3, 2, null);
            new Sprite("walldarkcorner", "walls.png", 6, 2, null);
            new Sprite("walldarkLL", "walls.png", 5, 2, null);
            new Sprite("walldarkLR", "walls.png", 9, 2, null);
            new Sprite("walldarkUR", "walls.png", 10, 2, null);
            new Sprite("walldarkroof", "walls.png", 5, 3, null);

            new Sprite("walldarkwindowleft", "walls.png", 5, 20, null);
            new Sprite("walldarkwindowright", "walls.png", 6, 20, null);
            new Sprite("walldarkwindowtop", "walls.png", 7, 20, null);
            new Sprite("walldarkwindowbottom", "walls.png", 8, 20, null);
            new Sprite("walldarkwindowup", "walls.png", 9, 20, null);
            new Sprite("walldarkwindowdown", "walls.png", 10, 20, null);
            new Sprite("walldarkwindowlinks", "walls.png", 11, 20, null);
            new Sprite("walldarkwindowrechts", "walls.png", 12, 20, null);

            new Sprite("walldarkwindowtopabove", "walls.png", 7, 21, null);
            new Sprite("walldarkwindowbottomabove", "walls.png", 8, 21, null);
            new Sprite("walldarkwindowupabove", "walls.png", 9, 21, null);
            new Sprite("walldarkwindowdownabove", "walls.png", 10, 21, null);
        }
    }

    private class ShuttleWallSet extends WallSet {
        public ShuttleWallSet(String name) {
            super(name);
            new Sprite("wallshuttletop", "shuttle.png", 3, 6, null);
            new Sprite("wallshuttleside", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlecorner", "shuttle.png", 5, 7, null);
            new Sprite("wallshuttleLL", "shuttle.png", 6, 7, null);
            new Sprite("wallshuttleLR", "shuttle.png", 7, 7, null);
            new Sprite("wallshuttleUR", "shuttle.png", 4, 7, null);
            new Sprite("wallshuttleroof", "shuttle.png", 0, 6, null);

            new Sprite("wallshuttlewindowleft", "shuttle.png", 3, 6, null);
            new Sprite("wallshuttlewindowright", "shuttle.png", 3, 6, null);
            new Sprite("wallshuttlewindowtop", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowbottom", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowup", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowdown", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowlinks", "shuttle.png", 0, 11, null);
            new Sprite("wallshuttlewindowrechts", "shuttle.png", 0, 11, null);

            new Sprite("wallshuttlewindowtopabove", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowbottomabove", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowupabove", "shuttle.png", 8, 6, null);
            new Sprite("wallshuttlewindowdownabove", "shuttle.png", 8, 6, null);
        }
    }
}
