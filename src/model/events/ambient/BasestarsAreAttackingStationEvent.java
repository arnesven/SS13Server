package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.BombItem;
import model.items.weapons.Missile;
import model.map.GameMap;
import model.map.Room;
import model.objects.consoles.AIConsole;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by erini02 on 07/12/16.
 */
public class BasestarsAreAttackingStationEvent extends AmbientEvent {

    private boolean hasHappened = false;
    private int sideA;
    private int sideB;
    private Integer[] attackCoordinates;

    @Override
    protected double getStaticProbability() {
        return AmbientEvent.everyNGames(8);
    }

    @Override
    public void apply(GameData gameData) {
        Integer[] currentCoords = gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME);
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            hasHappened = true;

            basestarsJumpIn(gameData);
        } else if (hasHappened && currentCoords.equals(attackCoordinates)) {
            List<BombItem> shotsFired = new ArrayList<>();
            basestarAttackSide(gameData, sideA, shotsFired);
            basestarAttackSide(gameData, sideB, shotsFired);
            basestarsAttacksFinalize(gameData, shotsFired);
        }
    }


    private void basestarAttackSide(GameData gameData, int side, List<BombItem> shotsFired) {
        Collection<Room> targetedRooms = gameData.getMap().getArea(GameMap.STATION_LEVEL_NAME, GameMap.getSideString(side));
        int numShots = MyRandom.nextInt(2)+2;
        for (int i = numShots; i > 0; --i) {
            BombItem missile = new Missile();
            MyRandom.sample(targetedRooms).addItem(missile);
            shotsFired.add(missile);
        }
    }

    private void basestarsAttacksFinalize(GameData gameData, List<BombItem> shotsFired) {
        for (BombItem it : shotsFired) {
            if (!it.isExploded()) {
                it.explode(gameData, null);
            }
        }

        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("The basestars are firing on. Take cover!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }


    private void basestarsJumpIn(GameData gameData) {
        sideA = MyRandom.nextInt(4);
        sideB = MyRandom.nextInt(4);
        attackCoordinates = gameData.getMap().getPositionForLevel(GameMap.STATION_LEVEL_NAME);

        try {
            StringBuilder dirString = new StringBuilder("to the ");
            dirString.append(GameMap.getSideString(sideA));
            if (sideA == sideB) {
                dirString.insert(0, "both ");
            } else {
                dirString.append(" and the " + GameMap.getSideString(sideB));
            }
            dirString.append(" side of the station.");

            gameData.findObjectOfType(AIConsole.class).informOnStation("Two basestars have jumped into this sector " + dirString.toString(), gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
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
