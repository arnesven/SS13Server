package model.map.rooms;

import model.GameData;
import model.items.EmptyContainer;
import model.items.general.Chemicals;
import model.map.doors.Door;
import model.map.doors.UpgoingStairsDoor;
import model.npcs.RecyclotronNPC;
import model.objects.decorations.OldChair;
import model.objects.decorations.PosterObject;
import model.objects.decorations.TrashBag;
import model.objects.decorations.WetWarningSign;
import model.objects.general.JanitorialStorage;
import model.objects.recycling.RecyclingContainer;

public class JanitorialRoom extends SupportRoom {
    public JanitorialRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doors) {
        super(id, "Janitorial", "", x, y, w, h, ints, doors);
        setZ(-1);
        this.addObject(new UpgoingStairsDoor(this));
        this.addObject(new WetWarningSign(this));
        this.addObject(new TrashBag(this));
        this.addObject(new OldChair(this));
        this.addObject(new RecyclingContainer(this));
        this.addObject(new JanitorialStorage(this));

    }

    @Override
    protected boolean getsTrashBin() {
        return false;
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        gameData.addNPC(new RecyclotronNPC(this));
    }
}
