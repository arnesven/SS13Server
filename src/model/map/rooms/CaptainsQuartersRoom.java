package model.map.rooms;

import model.GameData;
import model.items.general.MoneyStack;
import model.items.general.NuclearDisc;
import model.items.general.TimeBomb;
import model.map.doors.Door;
import model.map.floors.CaptainsQuartersFloorSet;
import model.map.floors.FloorSet;
import model.npcs.EyeballAlienNPC;
import model.npcs.NPC;
import model.npcs.PirateNPC;
import model.npcs.animals.CatNPC;
import model.objects.decorations.ComfyChair;
import model.objects.decorations.NiceBed;
import model.objects.general.PulseRifleDisplayCase;
import model.objects.general.SlotMachine;
import sounds.Sound;

/**
 * Created by erini02 on 15/12/16.
 */
public class CaptainsQuartersRoom extends CommandRoom {
    public CaptainsQuartersRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Captain's Quarters", "CQ", x, y, w, h, ints, doubles);
        NPC cat = new CatNPC(this);
        addItem(new NuclearDisc(gameData, true));
        addItem(new MoneyStack(300));
        addObject(new PulseRifleDisplayCase(this), RelativePositions.CENTER);
        gameData.addNPC(cat);
        addObject(new ComfyChair(this), RelativePositions.MID_BOTTOM);
        addObject(new NiceBed(this), RelativePositions.LOWER_RIGHT_CORNER);
    }

    @Override
    public FloorSet getFloorSet() {
        return new CaptainsQuartersFloorSet();
    }

    @Override
    protected boolean getsTrashBin() {
        return false;
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambidet2");
    }
}
