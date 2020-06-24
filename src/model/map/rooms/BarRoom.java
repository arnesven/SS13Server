package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.npcs.BAR2D2Robot;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.BarSignControl;
import model.objects.decorations.JukeBox;
import model.objects.decorations.PosterObject;
import model.objects.general.Refrigerator;
import model.objects.general.SlotMachine;
import sounds.Sound;

/**
 * Created by erini02 on 28/04/16.
 */
public class BarRoom extends SupportRoom implements JukeBoxRoom {
    private String tune;

    public BarRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doors) {
        super(id, "Bar", "Bar", x, y, w, h, ints, doors);
        this.addObject(new Refrigerator(this), RelativePositions.MID_BOTTOM);
        this.addObject(new BarSignControl(this), RelativePositions.UPPER_RIGHT_CORNER);
        this.addObject(new SlotMachine(this), RelativePositions.UPPER_RIGHT_CORNER);
        this.addObject(new JukeBox(this), RelativePositions.UPPER_LEFT_CORNER);
        RobotNPC bar2d2 = new BAR2D2Robot(this.getID(), this);
        addObject(new PosterObject(this, "eatposter", 3, 5, 0.5));

        gameData.addNPC(bar2d2);
        tune = "ambidet1";
    }

    @Override
    public Sound getSpecificAmbientSound() {
        if (!tune.equals("nothing")) {
            return new Sound(tune);
        }
        return null;
    }

    public void setAmbientSound(String selectedTune) {
        tune = selectedTune;
    }
}
