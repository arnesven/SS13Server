package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.objects.AlienEggObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class AlienEggNonHuntModeEvent extends AmbientEvent {


    private static final double EGG_SPAWN_CHANCE_PER_TURN = 0.05; // 5 % per turn
    private static final double ALIEN_NPC_SPAWN_CHANCE_PER_TURN_AND_EGG = 0.01; // 1 % per turn and egg;
    private List<AlienEggObject> spawnedEggs = new ArrayList<>();


    @Override
    public void apply(GameData gameData) {
        if (MyRandom.nextDouble() < getProbability()) {
            Room targetRoom;
            int tries = 0;
            do {
                targetRoom = MyRandom.sample(gameData.getMap().getStationRooms());
                tries++;
            } while (targetRoom.getActors().isEmpty() || tries > 100);
            spawnedEggs.add(0, new AlienEggObject(1, targetRoom));
            targetRoom.addObject(spawnedEggs.get(0));
            Logger.log("Spawned alien egg in " + targetRoom.getName());
        }

        List<AlienEggObject> eggsToCheck = new ArrayList<>();
        eggsToCheck.addAll(spawnedEggs);
        for (AlienEggObject egg : eggsToCheck) {
            if (MyRandom.nextDouble() < ALIEN_NPC_SPAWN_CHANCE_PER_TURN_AND_EGG) {
                egg.splitOffIntoEggNPC(gameData);
                spawnedEggs.remove(egg);
            }
        }

    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return null;
    }

    @Override
    public SensoryLevel getSense() {
        return null;
    }

    @Override
    protected double getStaticProbability() {
        return EGG_SPAWN_CHANCE_PER_TURN;
    }
}
