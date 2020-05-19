package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Hazard;
import model.Player;
import model.events.ambient.ElectricalFire;
import util.Logger;
import util.MyRandom;

public class FireStaff extends StaffWeapon {
    public FireStaff() {
        super("Fire Staff", true);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("firestaff", "weapons2.png", 5, 27, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A powerful magical weapon, designed by mystical sages to propel fireballs at the wizard's adversary.";
    }

    @Override
    protected void checkHazard(Actor performingClient, GameData gameData) {
        super.checkHazard(performingClient, gameData);
        new Hazard(gameData) {
            @Override
            public void doHazard(GameData gameData) {
                if (MyRandom.nextDouble() < 0.15) {
                    ElectricalFire fire = ((ElectricalFire)gameData.getGameMode().getEvents().get("fires"));
                    fire.startNewEvent(performingClient.getPosition());
                    Logger.log(Logger.INTERESTING,
                            performingClient.getBaseName() + " started a fire!");
                }
            }
        };


    }
}
