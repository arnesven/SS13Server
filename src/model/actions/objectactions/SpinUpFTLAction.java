package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.consoles.FTLControl;

import java.util.List;

public class SpinUpFTLAction extends Action {

    private FTLControl ftlControl;

    public SpinUpFTLAction(FTLControl ftlControl) {
        super("Spin Up FTL", SensoryLevel.OPERATE_DEVICE);
        this.ftlControl = ftlControl;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with FTL console";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ftlControl.setSpunUp(true);
        performingClient.addTolastTurnInfo("You spun up the FTL.");
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation("FTL Spun up - prepare to jump.", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
