package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.floors.AIRoomFloorSet;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.ai.AITurret;
import model.objects.consoles.AIConsole;
import sounds.Sound;

/**
 * Created by erini02 on 15/12/16.
 */
public class AIRoom extends TechRoom {
    private AIConsole aiCons;

    public AIRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "AI Core", "AI", x, y, w, h, ints, doubles);
        setZ(+1);
        aiCons = new AIConsole(this);
        addObject(aiCons, new RelativePositions.SouthOf(aiCons.getScreen()));
        addObject(new AITurret(this, aiCons, gameData), RelativePositions.MID_TOP);
        addObject(new DowngoingStairsDoor(this), RelativePositions.UPPER_RIGHT_CORNER);
        if (aiCons.AIIsPlayer()) {
            setFloorSet(new SingleSpriteFloorSet("aifloorplayer", 5, 5));
        }

    }

    @Override
    public FloorSet getFloorSet() {
        return new AIRoomFloorSet();
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambimine");
    }
}
