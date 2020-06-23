package model.map.rooms;

import model.map.doors.Door;
import model.objects.*;
import model.objects.decorations.ComfyChair;
import model.objects.decorations.JukeBox;
import model.objects.general.GameObject;
import model.objects.general.SlotMachine;
import sounds.Sound;

public class ThroneRoom extends NewAlgiersRoom {
    public ThroneRoom(int i, int i1, int i2, int i3, int i4, int[] ints, Door[] doors) {
        super(i, "Throne Room", i1, i2, i3, i4, ints, doors);
        addObject(new SlotMachine(this), RelativePositions.MID_LEFT);
        addObject(new SlotMachine(this), RelativePositions.MID_LEFT);
        addObject(new SlotMachine(this), RelativePositions.MID_RIGHT);
        addObject(new JukeBox(this), RelativePositions.MID_RIGHT);
        LongVerticalTable tab = new LongVerticalTable(this);
        addObject(tab, RelativePositions.CENTER);
        GameObject comfy = new ComfyChair(this);
        addObject(comfy, new RelativePositions.NorthOf(tab));
        addObject(new LongSofa(this), RelativePositions.MID_TOP);
        GameObject chair = new RightChair(this);
        addObject(chair, new RelativePositions.WestOf(tab));
        addObject(new RightChair(this), new RelativePositions.SouthOf(chair));

        chair = new LeftChair(this);
        addObject(chair, new RelativePositions.EastOf(tab));
        addObject(new LeftChair(this), new RelativePositions.SouthOf(chair));

        //addObject(new PirateFeast(this), new RelativePositions.SouthOf(comfy));


    }

}
