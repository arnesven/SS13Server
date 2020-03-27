package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.TeleportAction;
import model.actions.objectactions.BeamUpAction;
import model.actions.objectactions.ScanSpaceAction;
import model.actions.objectactions.ShowTheseCoordinates;
import model.actions.objectactions.TeleportToCoordinatesAction;
import model.items.general.GameItem;
import model.items.general.Teleporter;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class TeleportConsole extends Console {


    public TeleportConsole(Room r) {
        super("Teletronics", r);

    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("teletronics", "teleporter.png", 1, 2, this);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {


        at.add(new CombinedTeleportAction(this, gameData));

    }

    private void addIfSuitable(List<Room> markedRooms, GameItem it) {
        if (it instanceof Teleporter) {
            Room r = ((Teleporter) it).getMarked();
            if (r != null) {
                markedRooms.add(r);
            }
        }
    }

    public List<Room> getMarkedRooms(GameData gameData) {
        List<Room> markedRooms = new ArrayList<>();
        for (Room r : gameData.getAllRooms()) {
            for (GameItem it : r.getItems()) {
                addIfSuitable(markedRooms, it);
            }
            for (Actor a : r.getActors()) {
                for (GameItem it : a.getItems()) {
                    addIfSuitable(markedRooms, it);
                }
            }
        }

//        try {
//            markedRooms.add(gameData.getRoom("Derelict Lab"));
//        } catch (NoSuchThingException e) {
//            e.printStackTrace();
//        }

        return markedRooms;
    }

    private class CombinedTeleportAction extends Action {
        private final List<Action> innerActions;
        private Action selected;

        public CombinedTeleportAction(TeleportConsole teleportConsole, GameData gameData) {
            super("Teletronics", SensoryLevel.OPERATE_DEVICE);
            innerActions = new ArrayList<>();

            List<Room> markedRooms = getMarkedRooms(gameData);
            if (markedRooms.size() > 0) {
                for (Room r : markedRooms) {
                    Teleporter tele = new Teleporter();
                    tele.setMarked(r);
                    innerActions.add(new TeleportAction(tele));
                }
            }
            innerActions.add(new TeleportToCoordinatesAction());
            innerActions.add(new BeamUpAction(teleportConsole, gameData));
            innerActions.add(new ShowTheseCoordinates(teleportConsole, gameData));
            innerActions.add(new ScanSpaceAction(gameData));

        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with Teletronics";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            selected.doTheAction(gameData, performingClient);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (Action a : innerActions) {
                if (args.get(0).equals(a.getName())) {
                    selected = a;
                    selected.setArguments(args.subList(1, args.size()), performingClient);
                }
            }
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            for (Action a : innerActions) {
                opts.addOption(a.getOptions(gameData, whosAsking));
            }
            return opts;
        }
    }
}
