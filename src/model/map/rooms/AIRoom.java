package model.map.rooms;

import model.GameData;
import model.objects.ai.AITurret;
import model.objects.consoles.AIConsole;
import model.objects.consoles.BotConsole;

/**
 * Created by erini02 on 15/12/16.
 */
public class AIRoom extends TechRoom {
    public AIRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, double[] doubles) {
        super(id, "AI Core", "AI", x, y, w, h, ints, doubles);
        AIConsole aiCons = new AIConsole(this);
        addObject(aiCons);
        addObject(new BotConsole(this));
        addObject(new AITurret(this, aiCons, gameData));
    }
}
