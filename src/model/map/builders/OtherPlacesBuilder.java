package model.map.builders;

import model.GameData;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.doors.AirLockDoor;
import model.map.doors.Door;
import model.map.doors.FullyOpenAirLockDoor;
import model.map.doors.NormalDoor;
import model.map.rooms.*;
import util.MyRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class OtherPlacesBuilder extends MapBuilder {


    private int FIELD_SIZE = 12;
    private DockingPoint dp;

    @Override
    protected void buildPart(GameData gameData, GameMap gm) {

        Room dummy = new DummyRoom(31, 18, 1, 0, 0, new int[]{28}, new Door[]{});
        gm.addRoom(dummy, ss13, "dummy");
        Room otherDim = new OtherDimension(32, new int[]{30}, new Door[]{});
        Room prisonPlanet = new PrisonPlanet(33);
        gm.createLevel("other dimension", "Planet");
        gm.addRoom(otherDim, "other dimension", "other dimension");
        gm.createLevel("prison planet", "Planet");
        gm.addRoom(prisonPlanet, "prison planet", "prison planet");

        Room deepspace = new SpaceRoom(41, 6, 8, 3, 3);
        addEventsToSpaceRoom(deepspace, gameData);
        gm.createLevel("deep space", "Space");
        gm.addRoom(deepspace, "deep space", "deep space");

        gm.createLevel("centcom", "Space");
        Room centComSpace = new SpaceRoom(923, 5, 5, 5, 5);
        addEventsToSpaceRoom(centComSpace, gameData);
        gm.addRoom(centComSpace, "centcom", "centcom");


        Room spectatorBench = new SpectatorRoom(gameData);
        gm.addRoom(spectatorBench, "ss13", "hidden");

        Room exoticPlanet = buildExoticPlanet(gameData, 42);
        gm.createLevel("exotic planet", "Planet");
        gm.addRoom(exoticPlanet, "exotic planet", "exotic planet");

        buildAsteroidField(gameData, gm);


    }

    private static ExoticPlanet buildExoticPlanet(GameData gameData, int i) {
        double d = MyRandom.nextDouble();
        if (d < 0.333) {
            return new JunglePlanet(i, gameData);
        } else if (d < 0.333) {
            return new DesertPlanet(i, gameData);
        } else {
            return new IcePlanet(i, gameData);
        }

    }

    private void buildAsteroidField(GameData gameData, GameMap gm) {

        int matrix[][] = fillRandomMatrix();

        // Add space for main room
        for (int w = 0; w < MiningStationRoom.MS_WIDTH; ++w) {
            for (int h = 0; h < MiningStationRoom.MS_HEIGHT; ++h) {
                matrix[FIELD_SIZE/2+w][FIELD_SIZE/2+h] = 1;
            }
        }
        matrix[FIELD_SIZE/2][FIELD_SIZE/2] = 3;
        matrix[FIELD_SIZE/2+2][FIELD_SIZE/2+1] = -1;

        // add space for cabin
        matrix[FIELD_SIZE/2-1][FIELD_SIZE/2+1] = 4;

        // add space for shuttle
        matrix[FIELD_SIZE/2+MiningStationRoom.MS_WIDTH][FIELD_SIZE/2+MiningStationRoom.MS_HEIGHT] = 5;
        matrix[FIELD_SIZE/2+MiningStationRoom.MS_WIDTH + 1][FIELD_SIZE/2+MiningStationRoom.MS_HEIGHT] = 1;

        List<Integer> asteroidId = new ArrayList<>();
        List<Room> asteroids = new ArrayList<>();
        Room miningStation = null;
        Room cabin = null;
        Room shuttle = null;

        gm.createLevel("asteroid field", "Space");
        SpaceRoom space = new SpaceRoom(100032, 0, 0, 0, 0);
        addEventsToSpaceRoom(space, gameData);
        gm.addRoom(space, "asteroid field", "asteroid field");

        int id = 43;
        for (int x = 0; x < FIELD_SIZE; ++x) {
            for (int y = 0; y < FIELD_SIZE; ++y) {
                if (matrix[x][y] == 0) {

                    int w = 1;
                    int h = 1;
                    if (x < FIELD_SIZE-1 && matrix[x+1][y] == 0) {
                        w++;
                        matrix[x+1][y] = 1;
                    } else if (y < FIELD_SIZE-1 && matrix[x][y+1] == 0) {
                        h++;
                        matrix[x][y+1] = 1;
                    }
                    asteroidId.add(id);
                    Room asteroid = new Asteroid(id++, x, y, w, h, gameData);
                    asteroids.add(asteroid);
                    GameMap.joinRooms(asteroid, space);
                    addEventsToSpaceRoom(asteroid, gameData);
                    gm.addRoom(asteroid, "asteroid field", "asteroid field");
                } else if (matrix[x][y] == 3) {
                    miningStation = new MiningStationRoom(x, y);
                    gm.addRoom(miningStation, "asteroid field", "mining station");
                    AirLockRoom air = new AirLockRoom(556, 99, x+2, y+1, 1, 1, new int[]{555},
                            new Door[]{new FullyOpenAirLockDoor(x+2, y+1.5, 0.0, 556, 555),
                                    new AirLockDoor(x+3,   y+1.5, 556, 100032),
                                    new AirLockDoor(x+2.5, y+2.0, 556, 100032)});
                    this.dp = new DockingPoint("Mining Station - 1", "Mining Ops", new Point(-1, 0), new Point(0, 1), air);
                    air.addDockingPoint(dp);
                    air.addDockingPoint(new DockingPoint("Mining Station - 2", "Mining Ops", new Point(-1, 0), new Point(1, 0), air));
                    gm.addRoom(air, "asteroid field", "mining station");
                } else if (matrix[x][y] == 4) {
                    cabin = new SupportRoom(id++, "Cabin", "", x, y, 1, 1, new int[]{},
                            new Door[]{new NormalDoor(x+1, y+0.5, id-1, 555)});
                    gm.addRoom(cabin, "asteroid field", "mining station");
                } else if (matrix[x][y] == 5) {
                    shuttle = new MiningShuttle(id++, "Mining Shuttle", "SHTL", x, y, 2, 1,
                            new int[]{}, new Door[]{}, gameData, this.dp);
                    gm.addRoom(shuttle, "asteroid field", "mining station");
                }
            }
        }


        GameMap.joinRooms(miningStation, cabin);
    }



    private int[][] fillRandomMatrix() {
        int matrix[][] = new int[FIELD_SIZE][FIELD_SIZE];
        for (int x = 0; x < FIELD_SIZE; ++x) {
            for (int y = 0; y < FIELD_SIZE; ++y) {
                matrix[x][y] = MyRandom.nextInt(3);
            }
        }
        return matrix;
    }
}
