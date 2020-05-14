package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.Bank;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.SellCrateAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.MarketConsoleFancyFrame;
import model.map.rooms.CargoBayRoom;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.MyRandom;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketConsole extends Console {

    //                          GameTurn    Money
    private Map<CrateObject, Pair<Integer, Integer>> valueMap;
    private List<String> history;
    private int totalSellValue;

    public MarketConsole(Room cargoBayRoom) {
        super("Market Console", cargoBayRoom);
        valueMap = new HashMap<>();
        history = new ArrayList<>();
        totalSellValue = 0;
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new SellCrateAction(gameData, cl, this));
        at.add(new SitDownAtConsoleAction(gameData, this) {
            @Override
            protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                return new MarketConsoleFancyFrame(performingClient, gameData, MarketConsole.this);
            }
        });
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
            msrp *= 1.2;
        }

        if (targetCrate.getInventory().size() > 2) {
            msrp *= 1.2;
        }

        msrp *= 1.0 + (MyRandom.nextDouble() - 0.5);

        valueMap.put(targetCrate, new Pair<>(gameData.getRound(), (int)msrp));
        return (int)msrp;
    }

    public int sellCrate(CrateObject targetCrate, GameData gameData, Actor seller) {
        int sellValue = getValueFor(targetCrate, gameData);
        totalSellValue += sellValue;
        gameData.getGameMode().getBank().addToStationMoney(sellValue);
        targetCrate.getPosition().removeObject(targetCrate);
        this.history.add(seller.getBaseName() + " sold " + targetCrate.getBaseName() + " for " + sellValue);
        return sellValue;
    }

    public int getTotalSellValue() {
        return totalSellValue;
    }
}
