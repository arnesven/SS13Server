package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.NoSuchThingException;
import model.objects.consoles.Console;
import util.Logger;

import java.util.ArrayList;

/**
 * Created by erini02 on 14/04/16.
 */
public abstract class Radio extends UplinkItem {

    private boolean noConnect = false;

    public Radio(String name, int cost) {
        super(name, 0.5, cost);
    }

    @Override
    public String getFullName(Actor whosAsking) {
        if (noConnect) {
            return super.getFullName(whosAsking) + " (no connection)";
        }
        return super.getFullName(whosAsking);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Console console;
        try {
            console = getSpecificConsole(gameData);
            if (console.isPowered() && !console.isBroken()) {
                noConnect = false;
                at.add(getSpecificAction(gameData));

            } else {
                noConnect = true;
            }
        } catch (NoSuchThingException e) {
            noConnect = true;
            Logger.log(Logger.CRITICAL, "console not found on station, not adding radio.");
        }

    }

    protected abstract Console getSpecificConsole(GameData gameData) throws NoSuchThingException;

    protected abstract Action getSpecificAction(GameData gameData);

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "<b>Connection: </b>" + (noConnect?"None":"OK");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("radio", "device.png", 52, this);
    }
}
