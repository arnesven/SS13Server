package model.map.rooms;

import model.GameData;
import model.characters.decorators.OxyMaskDecorator;
import model.map.doors.Door;
import model.objects.ai.SecurityCamera;
import model.objects.general.GameObject;
import model.objects.general.OxyMaskDispenser;

public class RemoteAirLockRoom extends AirLockRoom {
    public RemoteAirLockRoom(int i, int i1, int i2, int i3, int i4, int i5, int[] ints, Door[] doors) {
        super(i, i1, i2, i3, i4, i5, ints, doors);
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        getObjects().removeIf((GameObject obj) -> obj instanceof SecurityCamera);
        getObjects().removeIf((GameObject obj) -> obj instanceof OxyMaskDispenser);
    }
}
