package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.SellCrateAction;
import model.map.rooms.CargoBayRoom;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.MyRandom;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarketConsole extends Console {

    //                          GameTurn    Money
    private Map<CrateObject, Pair<Integer, Integer>> valueMap;

    public MarketConsole(Room cargoBayRoom) {
        super("Market Console", cargoBayRoom);
        valueMap = new HashMap<>();
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new SellCrateAction(gameData, cl, this));
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("marketconsole", "computer2.png", 2, 12, this);
    }

    public int getValueFor(CrateObject targetCrate, GameData gameData) {
        if (valueMap.keySet().contains(targetCrate) && valueMap.get(targetCrate).first == gameData.getRound()) {
            return valueMap.get(targetCrate).second;
        }

        double msrp = targetCrate.getMSRP();
        if (targetCrate.getInventory().size() > 1 && targetCrate.allSameType()) {
            msrp *= 1.1;
        }

        if (targetCrate.getInventory().size() > 4) {
            msrp *= 1.1;
        }

        msrp *= 1.0 + (MyRandom.nextDouble() - 0.5);

        valueMap.put(targetCrate, new Pair<>(gameData.getRound(), (int)msrp));
        return (int)msrp;
    }
}
