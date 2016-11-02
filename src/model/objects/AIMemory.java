package model.objects;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PullMemoryBlockAction;
import model.map.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 02/11/16.
 */
public class AIMemory extends GameObject {
    private final Player aiPlayer;

    public AIMemory(Player aIPlayer, Room room) {
        super("AI Memory Bank", room);
        this.aiPlayer = aIPlayer;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("aimemory", "stationobjs.png", 192);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
        if (!aiPlayer.isDead()) {
            at.add(new PullMemoryBlockAction(this, aiPlayer));
        }
    }

    public int getBlocks() {
        return (int)aiPlayer.getHealth();
    }
}
