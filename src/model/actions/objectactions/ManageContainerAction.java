package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.ManageContainerFancyFrame;
import model.objects.general.ContainerObject;
import sounds.Sound;

import java.util.List;

public class ManageContainerAction extends FreeAction {
    private final GameData gameData;
    private final ContainerObject container;

    public ManageContainerAction(GameData gameData, ContainerObject containerObject, Player p) {
        super("Retrieve/Store " + containerObject.getBaseName(), gameData, p);
        this.gameData = gameData;
        this.container = containerObject;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        p.getSoundQueue().add(new Sound("crate_open"));
        FancyFrame ff = new ManageContainerFancyFrame(p, gameData, container);
        p.setFancyFrame(ff);
    }


}
