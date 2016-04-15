package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.ActionOption;
import model.actions.SensoryLevel;
import model.characters.general.RobotCharacter;
import model.items.general.GameItem;
import model.items.general.RobotParts;
import model.npcs.RobotNPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;

import java.util.List;

/**
 * Created by erini02 on 14/04/16.
 */
public class BuildRobotAction extends Action {
    private RobotParts parts;
    private String chosenName;

    public BuildRobotAction(RobotParts robotParts) {
        super("Build Robot", SensoryLevel.PHYSICAL_ACTIVITY);
        this.parts = robotParts;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Built a robot!";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (String s : RobotNPC.getAvailableRobotNames()) {
            opts.addOption(s);
        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!performingClient.getItems().contains(parts)) {
            performingClient.addTolastTurnInfo("What? The parts are missing? Your action failed.");
            return;
        }
        performingClient.getItems().remove(this.parts);
        RobotNPC robot = new RobotNPC(new RobotCharacter(chosenName, 0, 20.0),
                new MeanderingMovement(0.0),
                new DoNothingBehavior(),
                performingClient.getPosition());
        RobotNPC.removeRobotName(chosenName);
        gameData.addNPC(robot);
        performingClient.addTolastTurnInfo("You built the a new bot; " + chosenName + "!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        this.chosenName = args.get(0);
    }
}
