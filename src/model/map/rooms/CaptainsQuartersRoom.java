package model.map.rooms;

import model.GameData;
import model.items.general.MoneyStack;
import model.items.general.NuclearDisc;
import model.npcs.NPC;
import model.npcs.animals.CatNPC;
import model.objects.general.PulseRifleDisplayCase;

/**
 * Created by erini02 on 15/12/16.
 */
public class CaptainsQuartersRoom extends Room {
    public CaptainsQuartersRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, double[] doubles, RoomType command) {
        super(id, "Captain's Quarters", "CQ", x, y, w, h, ints, doubles, command);
        NPC cat = new CatNPC(this);
        addItem(new NuclearDisc(gameData, true));
        addItem(new MoneyStack(300));
        addObject(new PulseRifleDisplayCase(this));
        gameData.addNPC(cat);

    }
}
