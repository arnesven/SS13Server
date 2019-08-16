package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.Action;
import model.actions.itemactions.RemoveDoorAction;
import model.characters.crew.ArchitectCharacter;
import model.characters.general.GameCharacter;

import java.util.ArrayList;

/**
 * Created by erini02 on 20/11/16.
 */
public class RoomPartsStack extends ItemStack {
    public RoomPartsStack(int number) {
        super("Construction Parts", 50.0, 1000, number);
    }



    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        if (cl.getCharacter().checkInstance(((GameCharacter gc) -> gc instanceof ArchitectCharacter))) {
            if (GameItem.hasAnItem(cl, new Tools())) {
                BuildNewRoomAction bnr = new BuildNewRoomAction();
                if (bnr.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                    at.add(bnr);
                }

                Action removeDoor = new RemoveDoorAction(cl);
                if (removeDoor.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                    at.add(removeDoor);
                }
            }
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("roomparts", "items.png", 45, this);
    }

    @Override
    public GameItem clone() {
        return new RoomPartsStack(getAmount());
    }
}
