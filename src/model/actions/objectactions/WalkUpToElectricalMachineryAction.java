package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.fancyframe.FancyFrame;
import model.fancyframe.UsingGameObjectFancyFrameDecorator;
import model.objects.general.ElectricalMachinery;

import java.util.List;

public abstract class WalkUpToElectricalMachineryAction extends FreeAction {

    private final GameData gameData;
    private final ElectricalMachinery machine;

    public WalkUpToElectricalMachineryAction(GameData gameData, Player pl, ElectricalMachinery machine) {
        super("Walk up to " + machine.getPublicName(pl), gameData, pl);
        this.gameData = gameData;
        this.machine = machine;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        FancyFrame ff = getFancyFrame(gameData, p);
        p.setFancyFrame(ff);
        p.setCharacter(new UsingGameObjectFancyFrameDecorator(p.getCharacter(), ff, machine));
    }


    protected abstract FancyFrame getFancyFrame(GameData gameData, Actor performingClient);


}
