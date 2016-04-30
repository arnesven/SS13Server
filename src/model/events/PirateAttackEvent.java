package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.SensoryLevel;
import model.map.Room;
import model.npcs.PirateNPC;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class PirateAttackEvent extends Event {
    private static final double CHANCE_OF_HAPPENING_EACH_ROUND = 1.025;
    private boolean hasHappened = false;
    private int piratesRemainingOnBarge;
    private int pirateNum = 1;
    private int randAirLock;
    private Room targetRoom;

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < CHANCE_OF_HAPPENING_EACH_ROUND) {
            hasHappened = true;
            randAirLock = MyRandom.nextInt(3)+1;
            targetRoom = randomTargetRoom(gameData);
            informCrew(gameData, randAirLock);


            piratesRemainingOnBarge = MyRandom.nextInt(5) + 4;
            movePiratesOverToStation(gameData, randAirLock);

        } else if (hasHappened & piratesRemainingOnBarge > 0) {
            movePiratesOverToStation(gameData, randAirLock);
        }
    }

    private Room randomTargetRoom(GameData gameData) {
        List<Room> roomList = new ArrayList<>();
        roomList.add(gameData.getRoom("Bridge"));
        roomList.add(gameData.getRoom("Generator"));
        roomList.add(gameData.getRoom("Bar"));
        roomList.add(gameData.getRoom("Greenhouse"));
        roomList.add(gameData.getRoom("Lab"));
        return MyRandom.sample(roomList);
    }

    private void informCrew(GameData gameData, int randAirLock) {
        for (Player p : gameData.getPlayersAsList()) {
            p.addTolastTurnInfo("AI; \"Warning! Pirate marauders are boarding the station through Air Lock #" +randAirLock + "!\"");
        }
    }

    private void movePiratesOverToStation(GameData gameData, int randAirLock) {
        Room airLock = gameData.getRoom("Air Lock #" + randAirLock);

        int piratesThisTurn = Math.min(piratesRemainingOnBarge, MyRandom.nextInt(3) + 1);

        for (int i = piratesThisTurn; i > 0; --i) {
            PirateNPC pirate = new PirateNPC(airLock, pirateNum++, targetRoom);

            gameData.addNPC(pirate);
            piratesRemainingOnBarge--;
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
