package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.CraftBombAction;
import model.items.general.GameItem;

import java.util.ArrayList;

public class EmptyContainer extends GameItem {
    public EmptyContainer() {
        super("Empty Container", 0.1, true, 0);
    }

    @Override
    public GameItem clone() {
        return new EmptyContainer();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("emptycontainer", "storage.png", 1, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        CraftBombAction cba = new CraftBombAction();
        if (cba.getOptions(gameData, cl).numberOfSuboptions() > 0) {
           at.add(cba);
        }
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An empty cannister which used to contain something. Useful for crafting other items.";
    }

}
