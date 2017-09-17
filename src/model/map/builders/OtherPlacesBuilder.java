package model.map.builders;

import model.GameData;
import model.map.GameMap;
import model.map.rooms.*;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class OtherPlacesBuilder extends MapBuilder {


    private int FIELD_SIZE = 12;

    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        Room dummy = new Room(31, "Dummy", "", 18, 1, 0, 0,
                new int[]{28}, new double[]{-1.0, -1.0}, RoomType.hidden);
        gm.addRoom(dummy, ss13, "dummy");
        Room otherDim = new OtherDimension(32, new int[]{30}, new double[]{-1.0, -1.0});
        Room prisonPlanet = new Room(33, "Prison Planet", "P R I S O N P L A N E T", 0, 0, 1, 1, new int[]{30}, new double[]{-1.0, -1.0}, RoomType.outer);

        gm.addRoom(otherDim, "other dimension", "other dimension");
        gm.addRoom(prisonPlanet, "prison planet", "prison planet");

        Room deepspace = new Room(41, "Deep Space", "D E E P   S P A C E", 6, 8, 3, 3, new int[]{}, new double[]{}, RoomType.space);
        addEventsToSpaceRoom(deepspace, gameData);
        gm.addRoom(deepspace, "deep space", "deep space");


        Room spectatorBench = new SpectatorRoom(gameData);
        gm.addRoom(spectatorBench, "ss13", "hidden");

        Room exoticPlanet = new ExoticPlanet(42, 2, 2, 6, 6, gameData);
        gm.addRoom(exoticPlanet, "exotic planet", "exotic planet");

        buildAsteroidField(gameData, gm);


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

        // add space for cabin
        matrix[FIELD_SIZE/2-1][FIELD_SIZE/2+1] = 4;

        // add space for shuttle
        matrix[FIELD_SIZE/2][FIELD_SIZE/2+MiningStationRoom.MS_HEIGHT] = 5;
        matrix[FIELD_SIZE/2+1][FIELD_SIZE/2+MiningStationRoom.MS_HEIGHT] = 1;

        List<Integer> asteroidId = new ArrayList<>();
        List<Room> asteroids = new ArrayList<>();
        Room miningStation = null;
        Room cabin = null;
        Room shuttle = null;

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
                    Room asteroid = new Asteroid(id++, x, y, w, h);
                    asteroids.add(asteroid);
                    addEventsToSpaceRoom(asteroid, gameData);
                    gm.addRoom(asteroid, "asteroid field", "asteroid field");
                } else if (matrix[x][y] == 3) {
                    miningStation = new MiningStationRoom(id++, x, y);
                    gm.addRoom(miningStation, "asteroid field", "mining station");
                } else if (matrix[x][y] == 4) {
                    cabin = new Room(id++, "Cabin", "", x, y, 1, 1, new int[]{}, new double[]{}, RoomType.support);
                    gm.addRoom(cabin, "asteroid field", "mining station");
                } else if (matrix[x][y] == 5) {
                    shuttle = new ShuttleRoom(id++, "Mining Shuttle", "SHTL", x, y, 2, 1, new int[]{}, new double[]{}, RoomType.tech, gameData);
                    gm.addRoom(shuttle, "asteroid field", "mining station");
                }
            }
        }

        for (Room asteroid : asteroids) {
            int[] arr = new int[asteroidId.size()];
            for (int i = 0; i < asteroidId.size(); ++i) {
                arr[i] = asteroidId.get(i);
            }
            asteroid.setNeighbors(arr);
        }

        GameMap.joinRooms(miningStation, cabin);
        GameMap.joinRooms(miningStation, shuttle);

        cabin.setDoors(new double[]{cabin.getX() + cabin.getWidth(), cabin.getY() + 0.5});
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
