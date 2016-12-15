package model.map.rooms;

import model.GameData;
import model.objects.AITurret;
import model.objects.consoles.AIConsole;
import model.objects.consoles.BotConsole;

/**
 * Created by erini02 on 15/12/16.
 */
public class AIRoom extends Room {
    public AIRoom(GameData gameData, int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType tech) {
        super(i, "AI Core", "AI", i1, i2, i3, i4, ints, doubles, tech);
        AIConsole aiCons = new AIConsole(this);
        addObject(aiCons);
        addObject(new BotConsole(this));
        addObject(new AITurret(this, aiCons, gameData));
    }
}
