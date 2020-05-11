package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.NoPressureEvent;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.consoles.AirLockConsole;
import util.Logger;

import java.util.*;

/**
 * Created by erini02 on 09/09/17.
 */
public class VentStationAction extends ConsoleAction {

    private String selectedPart;

    public VentStationAction() {
        super("Vent Station", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Life Support";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        try {
            AirLockConsole alc = gameData.findObjectOfType(AirLockConsole.class);
            if (alc.getVentApprovedRound() != gameData.getRound()) {
                performingClient.addTolastTurnInfo("Venting of station was not approved. " + failed(gameData, performingClient));
                return;
            }
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo("Could not locate airlock override console. " + failed(gameData, performingClient));
            e.printStackTrace();
        }

        Collection<Room> affectedRooms = new HashSet<>();
        Logger.log("Venting part " + selectedPart);
        if (selectedPart.equals("whole station")) {
            performingClient.addTolastTurnInfo("You vented the " + selectedPart);
            affectedRooms.addAll(gameData.getMap().getStationRooms());
        } else {
            performingClient.addTolastTurnInfo("You vented the " + selectedPart + " part of the station");
            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation("The " + selectedPart + " has been vented.", gameData);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
            affectedRooms.addAll(gameData.getMap().getArea(GameMap.STATION_LEVEL_NAME, selectedPart));
        }
        Map<Room, NoPressureEvent> eventMap = new HashMap<>();

        for (Room r : affectedRooms) {
            if (!NoPressureEvent.hasNoPressureEvent(r)) {
                NoPressureEvent npe = new NoPressureEvent(r, performingClient, false);
                eventMap.put(r, npe);
                r.addEvent(npe);
                gameData.addEvent(npe);
            }
        }

        gameData.addMovementEvent(new Event() {
            @Override
            public void apply(GameData gameData) {
                for (Map.Entry<Room, NoPressureEvent> entry : eventMap.entrySet()) {
                    gameData.removeEvent(entry.getValue());
                    entry.getKey().removeEvent(entry.getValue());
                }
            }

            @Override
            public boolean shouldBeRemoved(GameData gameData) {
                return true;
            }

            @Override
            public String howYouAppear(Actor performingClient) {
                return "";
            }

            @Override
            public SensoryLevel getSense() {
                return SensoryLevel.NO_SENSE;
            }
        });

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selectedPart = args.get(0);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts =  super.getOptions(gameData, whosAsking);

        for (String s : GameMap.getSS13AreaNames()) {
            opts.addOption(s);
        }
        opts.addOption("whole station");

        return opts;
    }
}
