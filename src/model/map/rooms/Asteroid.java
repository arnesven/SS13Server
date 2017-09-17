package model.map.rooms;

/**
 * Created by erini02 on 16/09/17.
 */
public class Asteroid extends Room {
    public Asteroid(int id, int x, int y, int w, int h) {
        super(id, "Asteroid " + id, "AST", x, y, w, h, new int[]{}, new double[]{}, RoomType.hall);
    }
}
