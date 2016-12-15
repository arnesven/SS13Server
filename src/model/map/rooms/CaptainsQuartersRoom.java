package model.map.rooms;

import model.GameData;
import model.items.general.MoneyStack;
import model.items.general.NuclearDisc;
import model.npcs.NPC;
import model.npcs.animals.CatNPC;
import model.objects.PulseRifleDisplayCase;

/**
 * Created by erini02 on 15/12/16.
 */
public class CaptainsQuartersRoom extends Room {
    public CaptainsQuartersRoom(GameData gameData, int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType command) {
        super(i, "Captain's Quarters", "CQ", i1, i2, i3, i4, ints, doubles, command);
        NPC cat = new CatNPC(this);
        addItem(new NuclearDisc(gameData, true));
        addItem(new MoneyStack(300));
        addObject(new PulseRifleDisplayCase(this));
        gameData.addNPC(cat);

    }
}
