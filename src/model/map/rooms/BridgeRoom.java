package model.map.rooms;

import model.items.general.FireExtinguisher;
import model.items.suits.SpaceSuit;
import model.map.doors.Door;
import model.objects.BridgeChair;
import model.objects.consoles.AirLockConsole;
import model.objects.consoles.FTLControl;
import model.objects.consoles.MiningShuttleControl;
import model.objects.decorations.CaptainsBridgeChair;
import model.objects.general.GameObject;
import sounds.Sound;

/**
 * Created by erini02 on 15/12/16.
 */
public class BridgeRoom extends CommandRoom {
    public BridgeRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Bridge", "Brdg", x, y, w, h, ints, doubles);
        addItem(new SpaceSuit());
        addItem(new FireExtinguisher());
        addObject(new AirLockConsole(this), RelativePositions.MID_RIGHT);
        GameObject ftl = new FTLControl(this);
        addObject(ftl, RelativePositions.MID_RIGHT);
        GameObject shuttle = new MiningShuttleControl(this, true);
        addObject(shuttle, RelativePositions.MID_RIGHT);
        addObject(new CaptainsBridgeChair("Captain", this), RelativePositions.CENTER);
        addObject(new BridgeChair("XO", this), RelativePositions.MID_TOP);
        addObject(new BridgeChair("Helmsman", this), new RelativePositions.WestOf(shuttle));
        addObject(new BridgeChair("Navigator", this), new RelativePositions.WestOf(ftl));
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambitech");
    }

    @Override
    protected RelativePositions getTrashBinRelativePosition() {
        return RelativePositions.LOWER_LEFT_CORNER;
    }
}
