package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import sounds.Sound;

import java.util.ArrayList;

/**
 * Created by erini02 on 25/08/16.
 */
public class PackOfSmokes extends GameItem {
    public PackOfSmokes() {
        super("Pack of Smokes", 0.1, true, 20);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return  new Sprite("packofsmokes", "cigarettes.png", 0, this);
    }

    @Override
    public GameItem clone() {
        return new PackOfSmokes();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new SmokeAction());
    }

    @Override
    public Sound getDropSound() {
        return new Sound("matchbox_drop");
    }

    @Override
    public Sound getPickUpSound() {
        return new Sound("matchbox_pickup");
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A pack of Nanotrasen standard issue cigarettes. On the front is the following text in bold:<br/>" +
                "<i><center>Health Warning:<br/><br/>" +
                "Smoking these cigarettes will make babies cry, bring you bad luck and degrade the value of your stocks." +
                " It may also affect your health negatively." +
                "</center></i>";
    }
}
