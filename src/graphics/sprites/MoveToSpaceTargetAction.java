package graphics.sprites;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

public class MoveToSpaceTargetAction extends Action {


    private final double x;
    private final double y;
    private final double z;

    public MoveToSpaceTargetAction(double x, double y, double z) {
        super("Move to (x=" + x + " y=" + y + " z=" + z + ")", SensoryLevel.NO_SENSE);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "moved";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.getCharacter().getSpacePosition().setX(x);
        performingClient.getCharacter().getSpacePosition().setY(y);
        performingClient.getCharacter().getSpacePosition().setZ(z);
        performingClient.addTolastTurnInfo("You moved through space. (to " + x + " " + y + " " + z + ")");
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
