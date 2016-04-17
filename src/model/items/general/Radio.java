package model.items.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.objects.consoles.CrimeRecordsConsole;

import java.util.ArrayList;

/**
 * Created by erini02 on 14/04/16.
 */
public abstract class Radio extends GameItem {

    private boolean noConnect = false;

    public Radio(String name) {
        super(name, 0.5);
    }

    @Override
    public String getFullName(Actor whosAsking) {
        if (noConnect) {
            return super.getFullName(whosAsking) + " (no connection)";
        }
        return super.getFullName(whosAsking);
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, model.Player cl) {
        CrimeRecordsConsole console = CrimeRecordsConsole.find(gameData);
        if (console.isPowered(gameData) && !console.isBroken()) {
            noConnect = false;
            at.add(getSpecificAction(gameData));

        } else {
            noConnect = true;
        }
    }

    protected abstract Action getSpecificAction(GameData gameData);

    @Override
    protected char getIcon() {
        return 'd';
    }

}
