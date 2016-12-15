package model.map.rooms;

import model.GameData;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.SecurityCameraConsole;
import model.objects.general.EvidenceBox;

/**
 * Created by erini02 on 15/12/16.
 */
public class SecurityRoom extends Room {
    public SecurityRoom(GameData gameData, int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType security) {
        super(i, "Security Station", "SS", i1, i2, i3, i4, ints, doubles, security);
        addObject(new CrimeRecordsConsole(this, gameData));
        addObject(new EvidenceBox(this));
        addObject(new SecurityCameraConsole(this));
    }
}
