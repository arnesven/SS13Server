package model.map.rooms;

public class UnderpassRoom extends HallwayRoom {
    public UnderpassRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Underpass", "", x, y, w, h, ints, doubles);
        super.setZ(-1);
    }
}
