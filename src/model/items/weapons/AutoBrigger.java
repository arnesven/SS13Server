package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.consoles.CrimeRecordsConsole;

public class AutoBrigger extends Weapon {

    public AutoBrigger() {
        super("Auto Brigger", 0.95, 0.0, false, 0.5, false, 330);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);
        String failString = "Hmm, the AutoBrigger doesn't seem to have any effect on " + target.getName();
        try {
            CrimeRecordsConsole cons = gameData.findObjectOfType(CrimeRecordsConsole.class);
            if (target instanceof Actor) {
                Actor victim = (Actor)target;
                if (cons.getReportedActors().keySet().contains(victim)) {
                    cons.teleBrig(victim, gameData);
                    performingClient.addTolastTurnInfo(target.getName() + " was sent to the brig!");
                } else {
                    performingClient.addTolastTurnInfo(failString);
                }
            }
        } catch (NoSuchThingException e) {
            performingClient.addTolastTurnInfo(failString);
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("autobrigger", "items.png", 26, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A weapon that does no damage, but instead instantly teleports the target to SS13's brig.";
    }


    @Override
    public GameItem clone() {
        return new AutoBrigger();
    }
}
