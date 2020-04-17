package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.TeleportAction;
import model.actions.objectactions.BeamUpAction;
import model.actions.objectactions.ScanSpaceAction;
import model.actions.objectactions.ShowTheseCoordinates;
import model.actions.objectactions.TeleportToCoordinatesAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.TeleportConsoleFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Teleporter;
import model.map.rooms.Room;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class TeleportConsole extends Console {

    private static final int xoff = MyRandom.nextInt(100);
    private static final int yoff = MyRandom.nextInt(100);
    private static final int zoff = MyRandom.nextInt(100);

    private List<String> scanData;

    public TeleportConsole(Room r) {
        super("Teletronics", r);
        scanData = new ArrayList<>();
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("teletronics", "computer2.png", 12, 17, this);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new CombinedTeleportAction(this, gameData));
        if (cl instanceof Player) {
            at.add(new SitDownAtConsoleAction(gameData, this) {
                @Override
                protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                    return new TeleportConsoleFancyFrame(performingClient, console, gameData);
                }
            });
        }
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

    public Integer[] getLocalCoordinates(GameData gameData) {
        Integer[] coordinates = new Integer[0];
        try {
            coordinates = gameData.getMap().getPositionForLevel(gameData.getMap().getLevelForRoom(this.getPosition()).getName());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return new Integer[]{coordinates[0]*100+xoff, coordinates[1]*100+yoff, coordinates[2]*100+zoff};
    }

    public void doSpaceScan(GameData gameData) {
        scanData.clear();
        for (int x=0; x < 3; ++x) {
            for (int y=0; y < 3; ++y) {
                for (int z=0; z < 3; ++z) {
                    String levelname = gameData.getMap().getLevelForCoordinates(new Integer[]{x, y, z}, gameData);
                    String coordString = x+"**-"+y+"**-"+z+"**";
                    if (levelname.contains("emptylevel")) {

                    } else if (levelname.equals("derelict")) {
                        scanData.add(coordString + " unidentified object.");
                    } else if (levelname.contains("exotic planet")) {
                        scanData.add(coordString + " planet.");
                    } else if (levelname.contains("asteroid field")) {
                        scanData.add(coordString + " asteroid field.");
                    } else if (levelname.contains("deep space")) {
                        for (Room r : gameData.getMap().getRoomsForLevel(levelname)) {
                            if (r.getActors().size() > 0) {
                                scanData.add(coordString + " Someone tumbling through space!");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public List<String> getScannedData() {
        return scanData;
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
            innerActions.add(new ScanSpaceAction(teleportConsole));

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
                    selected.setActionTreeArguments(args.subList(1, args.size()), performingClient);
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
