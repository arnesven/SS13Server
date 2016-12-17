package model.map.rooms;

import model.GameData;
import model.objects.consoles.AdministrationConsole;

/**
 * Created by erini02 on 17/12/16.
 */
public class OfficeRoom extends Room {
    public OfficeRoom(GameData gameData, int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType command) {
        super(i, "Office", "Offc", i1, i2, i3, i4, ints, doubles, command);
        this.addObject(new AdministrationConsole(this, gameData));
    }
}
