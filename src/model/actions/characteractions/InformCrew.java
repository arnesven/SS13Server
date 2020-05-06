package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.GameMap;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingAvoidingMovement;
import model.npcs.behaviors.MoveTowardsBehavior;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by erini02 on 19/04/16.
 */
public class InformCrew extends Action {
    private final GameData gameData;
    private Actor selected;
    private String mess;
    private boolean gather;
    private String location;

    public InformCrew(GameData gameData) {
        super("Inform Crew",
                new SensoryLevel(SensoryLevel.VisualLevel.STEALTHY,
                        SensoryLevel.AudioLevel.INAUDIBLE,
                        SensoryLevel.OlfactoryLevel.UNSMELLABLE));
        this.gameData = gameData;

    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "Used PA microphone";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        ActionOption gather = new ActionOption("Gather in");
        for (Room r : gameData.getNonHiddenStationRooms()) {
            gather.addOption(r.getName());
        }
        opt.addOption(gather);

        ActionOption avoid = new ActionOption("Stay clear of");
        avoid.addOption("Aft side");
        avoid.addOption("Front side");
        avoid.addOption("Starboard side");
        avoid.addOption("Port side");
        for (Room r : gameData.getNonHiddenStationRooms()) {
            avoid.addOption(r.getName());
        }
        opt.addOption(avoid);

        return opt;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Room target = null;
        for (Room r : gameData.getMap().getStationRooms()) {
            if (r.getName().equals(location)) {
                target = r;
            }
        }

        if (gather) {
            if (target == null) {
                Logger.log("NO Location found for gather.");
            } else {
               speakOverPaInform("Please gather in " + location, gameData);
                for (NPC npc : gameData.getNPCs()) {
                    if (npc.getCharacter().isCrew() &&  // is crew and is on station
                            gameData.getNonHiddenStationRooms().contains(npc.getPosition())) {
                        npc.setMoveBehavior(new MoveTowardsBehavior(target,
                                npc.getMovementBehavior(), npc.getActionBehavior()));
                    }
                }
            }
        } else {
            List<Room> avoidRooms = new ArrayList<>();
            if (target == null) {
                Logger.log(Logger.CRITICAL,"NO Location found to avoid.");
                String cropped = location.replace(" side", "").toLowerCase();
                Collection<Room> list = gameData.getMap().getArea(GameMap.STATION_LEVEL_NAME, cropped);
                Logger.log("Side rooms: " + list);
                avoidRooms.addAll(list);
            } else {
                avoidRooms.add(target);
            }
            for (NPC npc : gameData.getNPCs()) {
                if (npc.getCharacter().isCrew() &&  // is crew and is on station
                        gameData.getNonHiddenStationRooms().contains(npc.getPosition())) {
                    npc.setMoveBehavior(new MeanderingAvoidingMovement(avoidRooms));
                }
            }

            speakOverPaInform("Please avoid " + location, gameData);
        }


    }

    private void speakOverPaInform(String s, GameData gameData) {
        for (Room r : gameData.getMap().getStationRooms()) {
            for (Actor a : r.getActors()) {
                a.addTolastTurnInfo(HTMLText.makeText("green", "HoS: " + s + "."));
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (args.get(0).equals("Gather in")) {
            this.gather = true;
        } else {
            this.gather = false;
        }

        this.location = args.get(1);

    }
}
