package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.BuildDoorAction;
import model.characters.crew.ArchitectCharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.ItemStack;
import model.items.general.Tools;
import model.items.tools.CraftingTools;

import java.util.ArrayList;

/**
 * Created by erini02 on 23/11/16.
 */
public class DoorPartsStack extends ItemStack {
    public DoorPartsStack(int i) {
        super("Door Parts", 10.0, 485, i);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("doorparts", "items.png", 46, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (cl.getCharacter().checkInstance(((GameCharacter gc) -> gc instanceof ArchitectCharacter))) {
            if (GameItem.hasAnItemOfClass(cl, CraftingTools.class)) {
                Action a = new BuildDoorAction(gameData, cl);
                if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                    at.add(a);
                }
            }
        }
    }

    @Override
    public GameItem clone() {
        return new DoorPartsStack(getAmount());
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "All the parts needed to build a new (normal) door";
    }
}
