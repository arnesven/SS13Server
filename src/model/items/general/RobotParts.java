package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.BuildRobotAction;
import model.characters.crew.RoboticistCharacter;
import model.characters.decorators.InstanceChecker;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;

import java.util.ArrayList;

/**
 * Created by erini02 on 14/04/16.
 */
public class RobotParts extends GameItem {


    public RobotParts() {
        super("Robot Parts", 125.0, 80);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("robotparts", "robots.png", 61);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
        super.addYourActions(gameData, at, cl);
        InstanceChecker instanceChecker = new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof RoboticistCharacter || ch instanceof RobotCharacter;
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
