package model.map.rooms;

import model.map.doors.Door;
import model.objects.LongSofa;
import model.objects.decorations.BunkBed;
import model.objects.decorations.PosterObject;
import model.objects.general.GameObject;
import model.objects.general.StasisPod;
import model.objects.general.Lockers;
import model.objects.general.Shower;

/**
 * Created by erini02 on 15/12/16.
 */
public class DormsRoom extends SupportRoom {
    public DormsRoom(int id, int x, int y, int w, int h, int[] ints, Door[] doors) {
        super(id, "Dorms", "Dorm", x, y, w, h, ints, doors);
        GameObject pod = new StasisPod(this);
        addObject(pod, RelativePositions.CENTER);
        addObject(new LongSofa(this), new RelativePositions.EastOf(pod));
        addObject(new Shower(this), RelativePositions.MID_LEFT);
        addObject(new BunkBed(this), RelativePositions.LOWER_LEFT_CORNER);
        addObject(new Lockers(this, 1), RelativePositions.MID_BOTTOM);
        addObject(new BunkBed(this), RelativePositions.MID_BOTTOM);
        addObject(new BunkBed(this), RelativePositions.LOWER_RIGHT_CORNER);
        addObject(new Lockers(this, 2), RelativePositions.LOWER_RIGHT_CORNER);
        addObject(new PosterObject(this, "idolposter", 0, 3, 0.5));
        addObject(new PosterObject(this, "lawposter", 7, 4, 2.0));
    }
}
