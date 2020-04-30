package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.SolarArrayControl;

import java.util.List;

public class SetSolarPanelRotationAction extends Action {
    private final SolarArrayControl sac;
    private int selectedAngle;

    public SetSolarPanelRotationAction(SolarArrayControl sac) {
        super("Set Solar Panel Rotation", SensoryLevel.OPERATE_DEVICE);
        this.sac = sac;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with " + this.sac.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt =  super.getOptions(gameData, whosAsking);
        for (int i = 0; i < 8; ++i) {
            opt.addOption((i*360)/8 + " Degrees");
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        this.sac.setAllConnectedSolarPanelRotation(selectedAngle);
        performingClient.addTolastTurnInfo("You rotated the solar panels.");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        int deg = Integer.parseInt(args.get(0).replace(" Degrees", ""));
        this.selectedAngle = (int)Math.round((deg*8.0) / 360.0);
    }
}
