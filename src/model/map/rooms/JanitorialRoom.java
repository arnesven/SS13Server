package model.map.rooms;

public class JanitorialRoom extends SupportRoom {
    public JanitorialRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Janitorial", "", x, y, w, h, ints, doubles);
        setZ(-1);
    }
}
