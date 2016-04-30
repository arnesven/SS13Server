package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.FrozenDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.objects.StasisPod;

import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 29/04/16.
 */
public class FreezeYourselfAction extends Action {
    private static final int MAX_NO_OF_ROUNDS = 2;
    private final StasisPod pod;
    private String timeChosen;

    public FreezeYourselfAction(StasisPod stasisPod) {
        super("Freeze Yourself", SensoryLevel.OPERATE_DEVICE);
        this.pod = stasisPod;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "got into the stasis pod.";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (int i = 1; i <= MAX_NO_OF_ROUNDS; ++i) {
            opt.addOption(i + " Round" + (i>1?"s":""));
        }
        opt.addOption("Forever");

        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (pod.isVacant()) {
            performingClient.addTolastTurnInfo("You got into the stasis pod.");
            pod.putIntoPod(gameData, performingClient, timeChosen);


        } else {
            performingClient.addTolastTurnInfo("What? The pod was already occupied? Your action failed.");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        timeChosen = args.get(0);
    }
}
