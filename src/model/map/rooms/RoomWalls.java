package model.map.rooms;

import graphics.sprites.Sprite;

import java.io.Serializable;

public class RoomWalls implements Serializable {

    private WallSet darkSet;
    private WallSet shuttleSet;
    private WallSet lightSet;

    public RoomWalls() {
        darkSet = new DarkWallSet("dark");
        lightSet = new LightWallSet("light");
        shuttleSet = new ShuttleWallSet("shuttle");
        new RightShuttleWallSet("shuttleright");
        new UpShuttleWallSet("shuttleup");
        new LeftShuttleWallSet("shuttleleft");
        new DownShuttleWallSet("shuttledown");

        new BadShuttleWallSet("badshuttle");
        new RightBadShuttleWallSet("badshuttleright");
        new UpBadShuttleWallSet("badshuttleup");
        new LeftBadShuttleWallSet("badshuttleleft");
        new DownBadShuttleWallSet("downshuttledown");

    }



    private class WallSet {
        private final String name;
        public WallSet(String name) {
            this.name = name;
        }
    }

    private class DarkWallSet extends WallSet {
        public DarkWallSet(String name) {
            super(name);
            new Sprite("wall" + name + "top", "walls.png", 12, 2, null);
            new Sprite("wall" + name + "side", "walls.png", 3, 2, null);
            new Sprite("wall" + name + "corner", "walls.png", 6, 2, null);
            new Sprite("wall" + name + "LL", "walls.png", 5, 2, null);
            new Sprite("wall" + name + "LR", "walls.png", 9, 2, null);
            new Sprite("wall" + name + "UR", "walls.png", 10, 2, null);
            new Sprite("wall" + name + "roof", "walls.png", 5, 3, null);

            new Sprite("wall" + name + "windowleft", "walls.png", 5, 20, null);
            new Sprite("wall" + name + "windowright", "walls.png", 6, 20, null);
            new Sprite("wall" + name + "windowtop", "walls.png", 7, 20, null);
            new Sprite("wall" + name + "windowbottom", "walls.png", 8, 20, null);
            new Sprite("wall" + name + "windowup", "walls.png", 9, 20, null);
            new Sprite("wall" + name + "windowdown", "walls.png", 10, 20, null);
            new Sprite("wall" + name + "windowlinks", "walls.png", 11, 20, null);
            new Sprite("wall" + name + "windowrechts", "walls.png", 12, 20, null);

            new Sprite("wall" + name + "windowtopabove", "walls.png", 7, 21, null);
            new Sprite("wall" + name + "windowbottomabove", "walls.png", 8, 21, null);
            new Sprite("wall" + name + "windowupabove", "walls.png", 9, 21, null);
            new Sprite("wall" + name + "windowdownabove", "walls.png", 10, 21, null);
        }
    }

    private class ShuttleWallSet extends WallSet {
        public ShuttleWallSet(String name) {
            super(name);
            new Sprite("wall" + name + "top", "shuttle.png", 3, 6, null);
            new Sprite("wall" + name + "side", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "roof", "shuttle.png", 0, 6, null);
            makeCorners(name);

            new Sprite("wall" + name + "windowleft", "shuttle.png", 3, 6, null);
            new Sprite("wall" + name + "windowright", "shuttle.png", 3, 6, null);
            new Sprite("wall" + name + "windowtop", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowbottom", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowup", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowdown", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowlinks", "shuttle.png", 3, 6, null);
            new Sprite("wall" + name + "windowrechts", "shuttle.png", 3, 6, null);

            new Sprite("wall" + name + "windowtopabove", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowbottomabove", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowupabove", "shuttle.png", 8, 6, null);
            new Sprite("wall" + name + "windowdownabove", "shuttle.png", 8, 6, null);
        }

        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 5, 7, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 6, 7, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 7, 7, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 4, 7, null);
        }
    }

    private class RightShuttleWallSet extends ShuttleWallSet {
        public RightShuttleWallSet(String shuttleright) {
            super(shuttleright);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 5, 7, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 6, 7, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 15, 6, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 12, 6, null);
        }
    }

    private class UpShuttleWallSet extends ShuttleWallSet {
        public UpShuttleWallSet(String shuttleup) {
            super(shuttleup);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 14, 6, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 6, 7, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 7, 7, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 12, 6, null);
        }
    }

    private class LeftShuttleWallSet extends ShuttleWallSet {
        public LeftShuttleWallSet(String shuttleleft) {
            super(shuttleleft);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 14, 6, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 13, 6, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 7, 7, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 4, 7, null);
        }

    }

    private class DownShuttleWallSet extends ShuttleWallSet {
        public DownShuttleWallSet(String shuttledown) {
            super(shuttledown);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 5, 7, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 1, 7, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 3, 7, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 4, 7, null);
        }

    }


    private class BadShuttleWallSet extends WallSet {
        public BadShuttleWallSet(String name) {
            super(name);
            new Sprite("wall" + name + "top", "shuttle.png", 15, 7, null);
            new Sprite("wall" + name + "side", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "roof", "shuttle.png", 6, 9, null);
            makeCorners(name);

            new Sprite("wall" + name + "windowleft", "shuttle.png", 15, 7, null);
            new Sprite("wall" + name + "windowright", "shuttle.png", 15, 7, null);
            new Sprite("wall" + name + "windowtop", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowbottom", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowup", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowdown", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowlinks", "shuttle.png", 15, 7, null);
            new Sprite("wall" + name + "windowrechts", "shuttle.png", 15, 7, null);

            new Sprite("wall" + name + "windowtopabove", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowbottomabove", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowupabove", "shuttle.png", 1, 8, null);
            new Sprite("wall" + name + "windowdownabove", "shuttle.png", 1, 8, null);
        }

        protected void makeCorners(String name) {}
    }

    private class RightBadShuttleWallSet extends BadShuttleWallSet{
        public RightBadShuttleWallSet(String badshuttleright) {
            super(badshuttleright);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 9, 8, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 10, 8, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 7, 8, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 4, 8, null);
        }
    }

    private class UpBadShuttleWallSet extends BadShuttleWallSet {
        public UpBadShuttleWallSet(String badshuttleup) {
            super(badshuttleup);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 6, 8, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 10, 8, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 11, 8, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 4, 8, null);
        }
    }

    private class LeftBadShuttleWallSet extends BadShuttleWallSet {
        public LeftBadShuttleWallSet(String badshuttleleft) {
            super(badshuttleleft);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 6, 8, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 5, 8, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 11, 8, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 8, 8, null);
        }
    }

    private class DownBadShuttleWallSet extends BadShuttleWallSet {
        public DownBadShuttleWallSet(String downshuttleleft) {
            super(downshuttleleft);
        }

        @Override
        protected void makeCorners(String name) {
            new Sprite("wall" + name + "corner", "shuttle.png", 9, 8, null);
            new Sprite("wall" + name + "LL", "shuttle.png", 5, 8, null);
            new Sprite("wall" + name + "LR", "shuttle.png", 7, 8, null);
            new Sprite("wall" + name + "UR", "shuttle.png", 8, 8, null);
        }
    }

    private class LightWallSet extends WallSet {
        public LightWallSet(String name) {
            super(name);
            new Sprite("wall" + name + "top", "walls.png", 18, 3, null);
            new Sprite("wall" + name + "side", "walls.png", 9, 3, null);
            new Sprite("wall" + name + "corner", "walls.png", 12, 3, null);
            new Sprite("wall" + name + "LL", "walls.png", 11, 3, null);
            new Sprite("wall" + name + "LR", "walls.png", 15, 3, null);
            new Sprite("wall" + name + "UR", "walls.png", 16, 3, null);
            new Sprite("wall" + name + "roof", "walls.png", 5, 3, null);

            new Sprite("wall" + name + "windowleft", "walls.png", 5, 21, null);
            new Sprite("wall" + name + "windowright", "walls.png", 6, 21, null);
            new Sprite("wall" + name + "windowtop", "walls.png", 13, 20, null);
            new Sprite("wall" + name + "windowbottom", "walls.png", 14, 20, null);
            new Sprite("wall" + name + "windowup", "walls.png", 15, 20, null);
            new Sprite("wall" + name + "windowdown", "walls.png", 16, 20, null);
            new Sprite("wall" + name + "windowlinks", "walls.png", 11, 21, null);
            new Sprite("wall" + name + "windowrechts", "walls.png", 12, 21, null);

            new Sprite("wall" + name + "windowtopabove", "walls.png", 7, 21, null);
            new Sprite("wall" + name + "windowbottomabove", "walls.png", 8, 21, null);
            new Sprite("wall" + name + "windowupabove", "walls.png", 9, 21, null);
            new Sprite("wall" + name + "windowdownabove", "walls.png", 10, 21, null);
        }
    }
}
