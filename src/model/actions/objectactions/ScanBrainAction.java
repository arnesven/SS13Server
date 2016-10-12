package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.Brain;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.consoles.BotConsole;

import java.util.List;

/**
 * Created by erini02 on 12/10/16.
 */
public class ScanBrainAction extends Action {

    private final BotConsole botConsole;
    private Brain selectedBrain;


    public ScanBrainAction(BotConsole botConsole) {
        super("Scan Brain", SensoryLevel.PHYSICAL_ACTIVITY);
        this.botConsole = botConsole;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "scanned a brain";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption ops = super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Brain) {
                ops.addOption(it.getPublicName(whosAsking));
            }
        }
        return ops;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.getItems().remove(selectedBrain);
        botConsole.addProgramFromBrain(gameData, selectedBrain);
        performingClient.addTolastTurnInfo("You scanned a brain.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it.getPublicName(performingClient).equals(args.get(0))) {
                selectedBrain = (Brain)it;
            }
        }

    }
}
