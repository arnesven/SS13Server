package model.items.general;

import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.objects.consoles.GeneratorConsole;

/**
 * Created by erini02 on 14/04/16.
 */
public class PowerRadio extends Radio {

    public PowerRadio() {
        super("Power Radio");
    }

    @Override
    protected Action getSpecificAction(GameData gameData) {
        return new PowerConsoleAction(GeneratorConsole.find(gameData));
    }

    @Override
    protected char getIcon() {
        return 'd';
    }



    @Override
    public GameItem clone() {
        return new PowerRadio();
    }
}
