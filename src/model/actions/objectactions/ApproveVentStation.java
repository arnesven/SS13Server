package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.objects.consoles.AirLockControl;

import java.util.List;

/**
 * Created by erini02 on 09/09/17.
 */
public class ApproveVentStation extends ConsoleAction {
    private final AirLockControl airlockControl;

    public ApproveVentStation(AirLockControl airLockControl) {
        super("Approve Venting", SensoryLevel.OPERATE_DEVICE);
        this.airlockControl = airLockControl;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with airlock control";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        airlockControl.setVentApprovedRound(gameData.getRound());
        performingClient.addTolastTurnInfo("You approved venting of the station this turn.");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
