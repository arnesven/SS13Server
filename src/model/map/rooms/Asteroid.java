package model.map.rooms;

import model.Actor;
import model.GameData;
import model.items.NoSuchThingException;
import model.items.mining.OreShard;
import model.map.GameMap;
import model.map.doors.Door;
import model.npcs.animals.AsteroidWorm;
import model.objects.general.GameObject;
import model.objects.mining.RockFactory;
import model.objects.mining.RockObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 16/09/17.
 */
public class Asteroid extends PlanetRoom {
    private static final double WORM_SPAWN_CHANCE = 0.05;

    public Asteroid(int id, int x, int y, int w, int h, GameData gameData) {
        super(id, "Asteroid " + id, "AST", x, y, w, h, new int[]{}, new Door[]{});

        for (double d = 0.9; d > MyRandom.nextDouble(); d = d/2.0) {
            addObject(RockFactory.randomRock(this));
        }

        if (MyRandom.nextDouble() < WORM_SPAWN_CHANCE) {
            gameData.addNPC(new AsteroidWorm(this));
        }

    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }

    @Override
    protected String getBackgroundStyle() {
        return "Space";
    }

    @Override
    public void destroy(GameData gameData) {
        //gameData.getMap().removeRoom(this);
        super.destroy(gameData);
        Room dest = shatterAllRocks(gameData);

        for (Room r : getNeighborList()) {
            for (Actor a : r.getActors()) {
                a.addTolastTurnInfo(this.getName() + " was destroyed! The remains landed on " + dest.getName() + ".");
            }
        }

    }

    private Room shatterAllRocks(GameData gameData) {
        List<RockObject> rocks = new ArrayList<>();
        for (GameObject obj : getObjects()) {
            if (obj instanceof RockObject) {
                rocks.add((RockObject) obj);
            }
        }

        List<OreShard> shards = new ArrayList<>();
        for (RockObject ro : rocks) {
            shards.addAll(ro.shatter());
        }

        Room destination = null;
        destination = GameMap.findClosest(gameData.getMap().getRoomsForLevel("asteroid field"), this);
        for (OreShard sh : shards) {
            destination.addItem(sh);
        }

        return destination;
    }
}
