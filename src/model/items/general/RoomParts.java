package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.Action;
import model.characters.crew.ArchitectCharacter;
import model.characters.general.GameCharacter;

import java.util.ArrayList;

/**
 * Created by erini02 on 20/11/16.
 */
public class RoomParts extends GameItem {
    public RoomParts() {
        super("Construction Parts", 125.0, false, 3000);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        if (cl.getCharacter().checkInstance(((GameCharacter gc) -> gc instanceof ArchitectCharacter))) {
            if (GameItem.hasAnItem(cl, new Tools())) {
                at.add(new BuildNewRoomAction());
            }
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("roomparts", "items.png", 45);
    }

    @Override
    public GameItem clone() {
        return new RoomParts();
    }
}
