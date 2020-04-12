package model.map.rooms;

import model.items.EmptyContainer;
import model.items.general.Chemicals;
import model.map.doors.UpgoingStairsDoor;
import model.objects.decorations.OldChair;
import model.objects.decorations.TrashBag;
import model.objects.decorations.WetWarningSign;

public class JanitorialRoom extends SupportRoom {
    public JanitorialRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "Janitorial", "", x, y, w, h, ints, doubles);
        setZ(-1);
        this.addObject(new UpgoingStairsDoor(this));
        this.addObject(new WetWarningSign(this));
        this.addObject(new TrashBag(this));
        this.addObject(new OldChair(this));
        this.addItem(new EmptyContainer());
        this.addItem(Chemicals.createRandomChemicals());
    }
}
