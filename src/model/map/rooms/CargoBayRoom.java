package model.map.rooms;

public class CargoBayRoom extends TechRoom {
    public CargoBayRoom(int id, int x, int y, int w, int h, int[] neighbors, double[] doors) {
        super(id, "Cargo Bay", "", x, y, w, h, neighbors, doors);
        setZ(-1);
    }
}
