package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.ambient.AmbientEvent;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.PirateShipRoom;
import model.map.rooms.Room;
import model.npcs.AbstractPirateNPC;
import model.npcs.NPC;
import model.npcs.PirateCaptainNPC;
import model.npcs.PirateNPC;
import model.npcs.behaviors.AttackAllActorsNotSameClassBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MoveTowardsBehavior;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateAttackEvent extends AmbientEvent {
    private static final double occurranceChance = AmbientEvent.everyNGames(5);
    private boolean hasHappened = false;
    private Set<AbstractPirateNPC> piratesOnBarge;
    private int pirateNum = 1;
    private int randAirLock;
    private Room targetRoom;
    private Room pirateShip;


    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {

            hasHappened = true;
            randAirLock = MyRandom.nextInt(3)+1;
            targetRoom = randomTargetRoom(gameData);
            informCrew(gameData, randAirLock);

            createPirateShip(gameData, randAirLock);

        } else if (hasHappened) {
            movePiratesOverToStation(gameData, randAirLock);
        }
    }

    private void createPirateShip(GameData gameData, int randAirLock) {
        pirateShip = new PirateShipRoom(gameData, randAirLock);
        gameData.getMap().addRoom(pirateShip, GameMap.STATION_LEVEL_NAME, "central");
        Room airLock = null;
        try {
            airLock = gameData.getRoom("Air Lock #" + randAirLock);
            GameMap.joinRooms(airLock, pirateShip);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


        int totalPirates = MyRandom.nextInt(5) + 4;
        int piratesToMoveDirectly = MyRandom.nextInt(3)+1;

        for (int i = totalPirates; i > 0; --i) {
            AbstractPirateNPC pirate;
            if (i == totalPirates && MyRandom.nextDouble() < 0.5) {
                pirate = new PirateCaptainNPC(pirateShip, targetRoom);
            } else {
                if (i <= piratesToMoveDirectly) {
                    pirate = new PirateNPC(airLock, pirateNum++, targetRoom);
                } else {
                    pirate = new PirateNPC(pirateShip, pirateNum++, targetRoom);
                    if (MyRandom.nextDouble() < 0.5) {
                        pirate.setMoveBehavior(new MeanderingMovement(0.0));
                    }
                }
            }
            gameData.addNPC(pirate);
        }

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

    private void informCrew(GameData gameData, int randAirLock) {
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("Warning! Pirate marauders are boarding the station through Air Lock #" +randAirLock + "!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    private void movePiratesOverToStation(GameData gameData, int randAirLock) {
        for (NPC npc : pirateShip.getNPCs()) {
            if (npc instanceof AbstractPirateNPC) {
                if (MyRandom.nextDouble() < 0.0) {
                    npc.setMoveBehavior(new MoveTowardsBehavior(targetRoom,
                            new MeanderingMovement(0.1), new AttackAllActorsNotSameClassBehavior()));
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
