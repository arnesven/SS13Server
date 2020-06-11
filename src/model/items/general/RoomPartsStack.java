package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.BuildNewRoomAction;
import model.actions.general.Action;
import model.actions.itemactions.ShowBuildNewRoomFancyFrame;
import model.characters.crew.ArchitectCharacter;
import model.characters.general.GameCharacter;
import model.items.tools.CraftingTools;

import java.util.ArrayList;

/**
 * Created by erini02 on 20/11/16.
 */
public class RoomPartsStack extends PartsItemStack {
    public RoomPartsStack(int number) {
        super("Construction Parts", 50.0, 1000, number);
    }


    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (cl.getCharacter().checkInstance(((GameCharacter gc) -> gc instanceof ArchitectCharacter))) {
            if (GameItem.hasAnItemOfClass(cl, CraftingTools.class)) {
                BuildNewRoomAction bnr = new BuildNewRoomAction();
                if (bnr.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                   // at.add(bnr);
                }

                if (cl instanceof Player) {
                    at.add(new ShowBuildNewRoomFancyFrame(gameData, (Player)cl, RoomPartsStack.this));
                }

//                Action removeDoor = new RemoveDoorAction(cl);
//                if (removeDoor.getOptions(gameData, cl).numberOfSuboptions() > 0) {
//                    at.add(removeDoor);
//                }
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

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "The raw materials to build a new air tight room on the station. However, only the architect is skilled enough to do so.";
    }


}
