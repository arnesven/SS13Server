package model.map.rooms;

import model.GameData;
import model.objects.consoles.AdministrationConsole;

/**
 * Created by erini02 on 17/12/16.
 */
public class OfficeRoom extends Room {
    public OfficeRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, double[] doubles, RoomType command) {
        super(id, "Office", "Offc", x, y, w, h, ints, doubles, command);
        this.addObject(new AdministrationConsole(this, gameData));
    }
}
