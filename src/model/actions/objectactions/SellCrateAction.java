package model.actions.objectactions;

import model.Actor;
import model.Bank;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.MarketConsole;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;

import java.util.List;

public class SellCrateAction extends Action {
    private final MarketConsole console;
    private String requestedCrate;

    public SellCrateAction(GameData gameData, Actor cl, MarketConsole marketConsole) {
        super("Sell Crate", SensoryLevel.OPERATE_DEVICE);
        this.console = marketConsole;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with " + console.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameObject obj : console.getPosition().getObjects()) {
            if (obj instanceof CrateObject) {
                opts.addOption(obj.getPublicName(whosAsking) + " for $$ " + console.getValueFor((CrateObject)obj, gameData));
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        CrateObject targetCrate = null;
        for (GameObject obj : console.getPosition().getObjects()) {
            if (obj instanceof CrateObject) {
                if (obj.getPublicName(performingClient).contains(requestedCrate) || requestedCrate.contains(obj.getPublicName(performingClient))) {
                    targetCrate = (CrateObject)obj;
                }
            }
        }

        if (targetCrate == null) {
            performingClient.addTolastTurnInfo("What? Crate wasn't there? " + failed(gameData, performingClient));
            return;
        }

        int sellValue = console.getValueFor(targetCrate, gameData);
        Bank.getInstance(gameData).addToStationMoney(sellValue);
        targetCrate.getPosition().removeObject(targetCrate);
        performingClient.addTolastTurnInfo("You sold the " + targetCrate.getPublicName(performingClient) +
                ". $$ " + sellValue + " was added to the station's funds!");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        requestedCrate = args.get(0);
    }
}
