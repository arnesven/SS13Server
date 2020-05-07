package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.ManageContainerFancyFrame;
import model.objects.general.ContainerObject;

import java.util.List;

public class ManageContainerAction extends Action {
    private final GameData gameData;
    private final ContainerObject container;

    public ManageContainerAction(GameData gameData, ContainerObject containerObject) {
        super("Retrieve/Store " + containerObject.getBaseName(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.gameData = gameData;
        this.container = containerObject;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Should not be executed
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new ManageContainerFancyFrame((Player)performingClient, gameData, container);
            ((Player)performingClient).setFancyFrame(ff);
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
