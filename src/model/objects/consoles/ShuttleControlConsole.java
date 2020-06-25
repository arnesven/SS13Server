package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CallEscapeShuttleAction;
import model.actions.objectactions.MiningShuttleAction;
import model.actions.objectactions.MoveShuttleAction;
import model.actions.objectactions.SitDownAtShuttleConsoleAction;
import model.map.GameMap;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ShuttleControlConsole extends Console {

    private final String levelA;
    private final String levelB;
    private String shuttleName;

    public ShuttleControlConsole(String name, Room r, String shuttleName, String levelA, String levelB) {
        super(name, r);
        this.shuttleName = shuttleName;
        this.levelA = levelA;
        this.levelB = levelB;
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.addAll(getNormalActions(gameData, cl));
        if (cl instanceof Player) {
            at.add(getSitDownAction(gameData, this, (Player)cl));
        }
    }

    protected abstract List<Action> getNormalActions(GameData gameData, Actor cl);

    protected abstract Action getSitDownAction(GameData gameData, ShuttleControlConsole shuttleControlConsole, Player cl);


    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("shuttlecontrol", "computer2.png", 0, 23, this);
    }

    public abstract MoveShuttleAction getCorrespondingAction(GameData gameData, Player player);

    public String getLevelA() {
        return levelA;
    }

    public String getLevelB() {
        return levelB;
    }

    public String getShuttleName() {
        return shuttleName;
    }
}
