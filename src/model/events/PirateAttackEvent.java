package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.ambient.AmbientEvent;
import model.items.NoSuchThingException;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.PirateShipRoom;
import model.map.rooms.Room;
import model.npcs.AbstractPirateNPC;
import model.npcs.NPC;
import model.npcs.PirateCaptainNPC;
import model.npcs.PirateNPC;
import model.npcs.behaviors.AttackNonPiratesBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MoveTowardsBehavior;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateAttackEvent extends AmbientEvent {
    private static final double occurranceChance = AmbientEvent.everyNGames(5);
    private static final double CHANCE_OF_PIRATE_CAPTAIN = 0.5;
    private boolean hasHappened = false;
    private int pirateNum = 1;
    private Room targetRoom;
    private PirateShipRoom pirateShip;


    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {

            targetRoom = randomTargetRoom(gameData);
            if (createPirateShip(gameData)) {
                hasHappened = true;
                Logger.log("Pirates docked at " + pirateShip.getDockingPointRoom().getName() +
                        " and their target is " + targetRoom.getName());
                informCrew(gameData);
            }

        } else if (hasHappened) {
            movePiratesOverToStation();
        }
    }

    private boolean createPirateShip(GameData gameData) {
        pirateShip = new PirateShipRoom(gameData);
        List<DockingPoint> dockingPoints = new ArrayList<>();
        for (DockingPoint dp : gameData.getMap().getLevel(GameMap.STATION_LEVEL_NAME).getDockingPoints()) {
            if (pirateShip.canDockAt(gameData, dp)) {
                dockingPoints.add(dp);
            }
        }
        if (dockingPoints.isEmpty()) {
            return false;
        }
        gameData.getMap().addRoom(pirateShip, GameMap.STATION_LEVEL_NAME, "central");
        pirateShip.dockYourself(gameData, MyRandom.sample(dockingPoints));


        int totalPirates = MyRandom.nextInt(5) + 4;
        int piratesToMoveDirectly = MyRandom.nextInt(3)+1;

        for (int i = totalPirates; i > 0; --i) {
            AbstractPirateNPC pirate;
            if (i == totalPirates && MyRandom.nextDouble() < CHANCE_OF_PIRATE_CAPTAIN) {
                pirate = new PirateCaptainNPC(pirateShip, targetRoom);
                pirate.setMoveBehavior(new MeanderingMovement(0.0));
            } else {
                if (i <= piratesToMoveDirectly) {
                    pirate = new PirateNPC(pirateShip.getDockingPointRoom(), pirateNum++, targetRoom);
                } else {
                    pirate = new PirateNPC(pirateShip.getDockingPointRoom(), pirateNum++, targetRoom);
                    if (MyRandom.nextDouble() < 0.5) {
                        pirate.setMoveBehavior(new MeanderingMovement(0.0));
                    }
                }
            }
            gameData.addNPC(pirate);
        }
        return true;
    }

    private Room randomTargetRoom(GameData gameData) {
        List<Room> roomList = new ArrayList<>();
        try {
            roomList.add(gameData.getRoom("Bridge"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Generator"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Bar"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Greenhouse"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Lab"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return MyRandom.sample(roomList);
    }

    private void informCrew(GameData gameData) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Warning! Pirate marauders are boarding " +
                    "the station through " + pirateShip.getDockingPointRoom().getName() + "!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void movePiratesOverToStation() {
        for (NPC npc : pirateShip.getNPCs()) {
            if (npc instanceof AbstractPirateNPC) {
                if (MyRandom.nextDouble() < 0.5) {
                    npc.setMoveBehavior(new MoveTowardsBehavior(targetRoom,
                            new MeanderingMovement(0.1), new AttackNonPiratesBehavior()));
                }
            }
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }

}
