package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.fancyframe.BarSignFancyFrame;
import model.fancyframe.ConsoleFancyFrame;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BarSignControl extends Console {
    public BarSignControl(Room barRoom) {
        super("Bar Sign Control", barRoom);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new BarSignAction(this));
        if (cl instanceof Player) {
            at.add(new SitDownAtConsoleAction(gameData, this, (Player)cl) {
                @Override
                protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                    return new BarSignFancyFrame(performingClient, BarSignControl.this, gameData);
                }
            });
        }
    }

    public static Map<String, Sprite> getSigns() {
        HashMap<String, Sprite> map = new HashMap<>();
        map.put("Armok's Bar N Grill", new Sprite("armoksbarsign1", "barsigns.png", 0, 0, 64, 32, null));
        map.put("The Orchard", new Sprite("theorchardbarsign1", "barsigns.png", 0, 3, 64, 32, null));
        map.put("Mead Bay", new Sprite("meadbaybarsign1", "barsigns.png", 0, 2, 64, 32, null));
        map.put("Whiskey Implant", new Sprite("whiskeybarsign1", "barsigns.png", 0, 4, 64, 32, null));

        return map;
    }
}
