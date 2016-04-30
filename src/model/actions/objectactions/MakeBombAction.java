package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.*;
import model.objects.general.Dumbwaiter;
import util.Logger;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 29/04/16.
 */
public class MakeBombAction extends Action {
    private static final double RISK_OF_EXPLOSION = 0.05;
    private Dumbwaiter cryoTanks;
    private String selected;

    public MakeBombAction(Dumbwaiter cryoTanks) {
        super("Make Bomb", SensoryLevel.PHYSICAL_ACTIVITY);
        this.cryoTanks = cryoTanks;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "built a bomb!";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        opt.addOption("Cryo Bomb");
        if (GameItem.hasAnItem(whosAsking, new Tools())) {
            opt.addOption("Riggable Bomb (destroys Tools)");
        } else if (hasUplinkItem(whosAsking) != null) {
            UplinkItem it = hasUplinkItem(whosAsking);
            opt.addOption("Remote Bomb (destroys " + it.getBaseName() + ")");
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        BombItem bomb;
        if (selected.contains("Riggable")) {
            bomb = new BoobyTrapBomb();
            if (GameItem.hasAnItem(performingClient, new Tools())) {
                try {
                    performingClient.getItems().remove(GameItem.getItem(performingClient, new Tools()));
                } catch (NoSuchThingException e) {
                    Logger.log(Logger.CRITICAL, "What, strange situation in Dumbwaiter");
                }
            } else {
                performingClient.addTolastTurnInfo("What? The tools are gone! Your action failed.");
                return;
            }
        } else if (selected.contains("Remote")) {
            bomb = new RemoteBomb();
            if (hasUplinkItem(performingClient) != null) {
                performingClient.getItems().remove(hasUplinkItem(performingClient));
            } else {
                performingClient.addTolastTurnInfo("What? Something is missing! Your action failed?");
                return;
            }
        } else {
            bomb = new BombItem("Cryo Bomb");
        }
        Logger.log(performingClient.getBaseName() + " built a bomb!");
        if (MyRandom.nextDouble() < RISK_OF_EXPLOSION) {
            performingClient.addTolastTurnInfo("Oops, something went wrong!");
            cryoTanks.getPosition().addItem(bomb);
            bomb.explode(gameData, performingClient);
        } else {
            performingClient.getCharacter().giveItem(bomb, null);
            performingClient.addTolastTurnInfo("You built a " + bomb.getBaseName() + "!");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selected = args.get(0);
    }


    private static UplinkItem hasUplinkItem(Actor whosAsking) {
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof  UplinkItem) {
                return (UplinkItem) it;
            }
        }
        return null;
    }
}
