package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.BuildElectronicObjectAction;
import model.actions.general.Action;
import model.characters.crew.EngineerCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.items.tools.CraftingTools;

import java.util.ArrayList;

/**
 * Created by erini02 on 17/11/16.
 */
public class ElectronicParts extends PartsGameItem {
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
            if (GameItem.hasAnItemOfClass(cl, CraftingTools.class)) {
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

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Various electronic parts needed to construct new electronic equipment. However, only an engineer (or a robot) would be skilled enough to use them.";
    }
}
