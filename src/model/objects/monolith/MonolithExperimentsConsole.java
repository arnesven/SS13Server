package model.objects.monolith;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.consoles.Console;

import java.util.ArrayList;

public class MonolithExperimentsConsole extends Console {
    public MonolithExperimentsConsole(Room r) {
        super("Experiment Console", r);
    }

    @Override
    public double getPowerConsumption() {
        return 0.000050; // 50 W when in standby mode...
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("experimentconsole", "computer2.png", 19, 23, this);
    }

    @Override
    protected Sprite getUnpoweredSprite(Player whosAsking) {
        return new Sprite("experimentconsolenopower", "computer2.png", 20, 23, this);
    }

    @Override
    protected Sprite getBrokenSprite(Player whosAsking) {
        return new Sprite("experimentconsolenobroken", "computer2.png", 21, 23, this);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {

    }
}
