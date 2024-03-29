package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.ButtBotCharacter;
import model.characters.general.RobotCharacter;
import model.items.NoSuchThingException;
import model.items.SeveredButt;
import model.items.SeveredHead;
import model.items.general.GameItem;
import model.items.general.RobotParts;
import model.npcs.robots.RobotNPC;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import sounds.Sound;

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

        if (GameItem.hasAnItemOfClass(whosAsking, SeveredButt.class)) {
            opts.addOption("ButtBot");
        }

        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!performingClient.getItems().contains(parts)) {
            performingClient.addTolastTurnInfo("What? The parts are missing? Your action failed.");
            return;
        }

        if (chosenName.equals("ButtBot")) {
            if (!GameItem.hasAnItemOfClass(performingClient, SeveredButt.class)) {
                performingClient.addTolastTurnInfo("What? No severed butt to use! Your action failed.");
                return;
            } else {
                try {
                    performingClient.getItems().remove(GameItem.getItemFromActor(performingClient, new SeveredHead(performingClient)));
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                performingClient.getItems().remove(this.parts);
                RobotNPC robot = new RobotNPC(new ButtBotCharacter(chosenName, 0, 20.0),
                        new MeanderingMovement(0.0),
                        new DoNothingBehavior(),
                        performingClient.getPosition());
                gameData.addNPC(robot);
            }
        } else {

            performingClient.getItems().remove(this.parts);
            RobotNPC robot = new RobotNPC(new RobotCharacter(chosenName, 0, 20.0),
                    new MeanderingMovement(0.0),
                    new DoNothingBehavior(),
                    performingClient.getPosition());
            RobotNPC.removeRobotName(chosenName);
            gameData.addNPC(robot);
        }

        performingClient.addTolastTurnInfo("You built the a new bot; " + chosenName + "!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        this.chosenName = args.get(0);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("rped");
    }
}
