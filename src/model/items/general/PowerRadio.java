package model.items.general;

import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.items.NoSuchThingException;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.GeneratorConsole;

/**
 * Created by erini02 on 14/04/16.
 */
public class PowerRadio extends Radio {

    public PowerRadio() {
        super("Power Radio");
    }

    @Override
    protected Console getSpecificConsole(GameData gameData) throws NoSuchThingException {
        return GeneratorConsole.find(gameData);
    }

    @Override
    protected Action getSpecificAction(GameData gameData) {
        try {
            return new PowerConsoleAction(GeneratorConsole.find(gameData));
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Cannot get specific action for Power Radio, no power console found on station.");
        }
    }

    @Override
    public GameItem clone() {
        return new PowerRadio();
    }
}
