package model.actions.ai;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ChangeScreenAction extends Action {
    private Point selected;
    private final static HashMap<String, Point> availableScreens = fillmap();



    public ChangeScreenAction(GameData gameData) {
        super("Change Screen", SensoryLevel.NO_SENSE);
        selected = new Point(0, 0);



    }

    public static Collection<Point> getAvailableScreens() {
        return availableScreens.values();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Changed Screen";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        try {
            AIConsole console = gameData.findObjectOfType(AIConsole.class);
            console.getScreen().setSelectedScreen(selected);
            performingClient.addTolastTurnInfo("You set the AI screen.");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts =  super.getOptions(gameData, whosAsking);
        for (String name : availableScreens.keySet()) {
            opts.addOption(name);
        }

        return opts;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (String name : availableScreens.keySet()) {
            if (name.equals(args.get(0))) {
                selected = availableScreens.get(name);
                break;
            }
        }
    }

    private static HashMap<String, Point> fillmap() {
        HashMap<String, Point> options = new HashMap<>();
        options.put("Normal", new Point(0, 0));
        options.put("Evil", new Point(0, 1));
        options.put("Pride", new Point(0, 4));
        options.put("Pride Clown", new Point(0, 5));
        options.put("Lava", new Point(0, 6));
        options.put("Upside-Down", new Point(0, 7));
        options.put("Inverted", new Point(11, 7));
        options.put("Colors", new Point(11, 8));
        options.put("Text", new Point(11, 14));
        options.put("Happyface", new Point(11, 16));
        options.put("Matrix", new Point(11, 17));
        options.put("Windows XP", new Point(2, 18));
        options.put("Stoneface", new Point(7, 18));
        options.put("Angryface", new Point(21, 16));
        options.put("Soviet", new Point(0, 21));
        options.put("Red Dot", new Point(4, 21));
        options.put("Fallout", new Point(5, 24));
        options.put("Love", new Point(8, 25));
        return options;
    }
}
