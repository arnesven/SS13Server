package model.objects.general;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import model.objects.power.PositronGenerator;

/**
 * Created by erini02 on 26/11/16.
 */
public class DerelictPowerSource extends PositronGenerator {
    public DerelictPowerSource(Room r, GameData gameData) {
        super(18.76, r, gameData);
        setHealth(0);
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        if (getPowerOutput() < 0.05) {
            return new Sprite("dpowersourcelow", "power.png", 7, 12, this);
        }
        return new Sprite("dpowersourcenormal", "power.png", 8, 12, this);
    }

    @Override
    public void doWhenRepaired(GameData gameData) {
        this.addToLevel(this.getStartingPower());
    }
}
