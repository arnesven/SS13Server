package model.items.general;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.itemactions.BuildRobotAction;
import model.characters.crew.RoboticistCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;

import java.util.ArrayList;

/**
 * Created by erini02 on 14/04/16.
 */
public class RobotParts extends GameItem {


    public RobotParts() {
        super("Robot Parts", 125.0);
    }

    @Override
    protected char getIcon() {
        return 'T';
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
        super.addYourActions(gameData, at, cl);
        InstanceChecker instanceChecker = new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof RoboticistCharacter;
            }
        };

        if (cl.getCharacter().checkInstance(instanceChecker)
            && GameItem.hasAnItem(cl, new Tools())) {
            at.add(new BuildRobotAction(this));
        }
    }

    @Override
    public GameItem clone() {
        return new RobotParts();
    }
}
