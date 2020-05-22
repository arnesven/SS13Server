package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.SecurityCameraConsole;
import model.objects.general.EvidenceBox;
import model.objects.general.SecurityStorage;

/**
 * Created by erini02 on 15/12/16.
 */
public class SecurityStationRoom extends SecurityRoom {
    public SecurityStationRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles, Room releaseRoom) {
        super(id, "Security Station", "SS", x, y, w, h, ints, doubles);
        addObject(new CrimeRecordsConsole(this, gameData, releaseRoom));
        addObject(new EvidenceBox(this));
        addObject(new SecurityCameraConsole(this));
        addObject(new SecurityStorage(this));
    }
}
