package model.objects.general;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.objects.consoles.PowerSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class DerelictPowerSource extends PowerSource {
    public DerelictPowerSource(Room r, GameData gameData) {
        super(18.76, r, gameData);
        setHealth(0);
    }

    @Override
    protected List<Room> getAffectedRooms(GameData gameData) {
        List<Room> result = new ArrayList<>();
        result.addAll(gameData.getMap().getRoomsForLevel("derelict"));
        return result;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (getPowerOutput() < 0.05) {
            return new Sprite("dpowersourcelow", "power.png", 7, 12);
        }
        return new Sprite("dpowersourcenormal", "power.png", 8, 12);
    }

    @Override
    public void doWhenRepaired(GameData gameData) {
        this.addToLevel(this.getStartingPower());
    }
}
