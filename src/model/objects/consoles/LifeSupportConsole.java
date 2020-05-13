package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.VentStationAction;
import model.map.rooms.GeneratorRoom;
import model.objects.general.GameObject;

import java.util.ArrayList;

/**
 * Created by erini02 on 09/09/17.
 */
@Deprecated
public class LifeSupportConsole extends Console {
    public LifeSupportConsole(GeneratorRoom generatorRoom, GameData gameData) {
        super("Life Support Console", generatorRoom);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new VentStationAction());
    }


    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("lifesupportconsole", "computer2.png", 3, 8, this);
    }
}
