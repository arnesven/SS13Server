package model.items.tools;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.BuildElectronicObjectAction;
import model.actions.general.Action;
import model.actions.itemactions.BuildDoorAction;
import model.actions.itemactions.DismantleAction;
import model.actions.itemactions.ShowBuildNewRoomFancyFrame;
import model.items.DoorPartsStack;
import model.items.NoSuchThingException;
import model.items.general.ElectronicParts;
import model.items.general.GameItem;
import model.items.general.RoomPartsStack;
import model.items.general.Tools;

import java.util.ArrayList;

public class CraftingTools extends Tools {

    public CraftingTools() {
        super("Crafting", 95);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("craftingtoolsheldinhand", "items_righthand.png", 1, 42, null);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("craftingtools", "storage.png", 14, this);
    }


    @Override
    protected String getToolsDescription(GameData gameData, Player performingClient) {
        return "Tool kit containing a wrench, a screwdriver, a pair of pliers, a laser-saw, a roll of duct tape, and pocket 3D-printer. Good for crafting stuff.";
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action dismantle = new DismantleAction(cl);
        if (dismantle.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(dismantle);
        }

        if (GameItem.hasAnItemOfClass(cl, DoorPartsStack.class)) {
            at.add(new BuildDoorAction(gameData, cl));
        }

        try {
            RoomPartsStack rps = GameItem.getItemFromActor(cl, new RoomPartsStack(1));
            if (cl instanceof Player) {
                at.add(new ShowBuildNewRoomFancyFrame(gameData, (Player)cl, rps));
            }
        } catch (NoSuchThingException e) {

        }

        if (GameItem.hasAnItemOfClass(cl, ElectronicParts.class)) {
            at.add(new BuildElectronicObjectAction(gameData));
        }
    }

    @Override
    public GameItem clone() {
        return new CraftingTools();
    }
}
