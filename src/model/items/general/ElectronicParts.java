package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.BuildElectronicObjectAction;
import model.actions.general.Action;
import model.characters.crew.EngineerCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/11/16.
 */
public class ElectronicParts extends GameItem {
    public ElectronicParts() {
        super("Electronics Parts", 1.0, true, 100);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("electronicsparts", "robots2.png", 6, 2, this);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof EngineerCharacter)) {
            if (GameItem.hasAnItem(cl, new Tools())) {
                at.add(new BuildElectronicObjectAction(gameData));
            }
        } else if ( cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RobotCharacter)) {
              at.add(new BuildElectronicObjectAction(gameData));
        }
    }

    @Override
    public GameItem clone() {
        return new ElectronicParts();
    }
}
