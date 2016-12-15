package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PullMemoryBlockAction;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 02/11/16.
 */
public class AIMemory extends GameObject {
    private final Player aiPlayer;
    private final GameCharacter aiCharacter;

    public AIMemory(Player aIPlayer, Room room) {
        super("AI Memory Bank", room);
        this.aiPlayer = aIPlayer;
        this.aiCharacter = aiPlayer.getCharacter();
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("aimemory", "stationobjs.png", 192);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (!aiPlayer.isDead()) {
            at.add(new PullMemoryBlockAction(this, aiPlayer, aiCharacter));
        }
    }

    public int getBlocks() {
        return (int)(aiCharacter.getHealth());
    }
}
