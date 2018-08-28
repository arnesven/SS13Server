package model.map.rooms;

import model.GameData;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.SecurityCameraConsole;
import model.objects.general.EvidenceBox;

/**
 * Created by erini02 on 15/12/16.
 */
public class SecurityRoom extends Room {
    public SecurityRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, double[] doubles, RoomType security, Room releaseRoom) {
        super(id, "Security Station", "SS", x, y, w, h, ints, doubles, security);
        addObject(new CrimeRecordsConsole(this, gameData, releaseRoom));
        addObject(new EvidenceBox(this));
        addObject(new SecurityCameraConsole(this));
    }
}
