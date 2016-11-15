package model.items.general;

import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.CrimeRecordsAction;
import model.actions.objectactions.ReportCrimeAction;
import model.items.NoSuchThingException;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;

public class SecurityRadio extends Radio {


	public SecurityRadio() {
		super("Sec Radio", 230);
	}
	

	@Override
	public SecurityRadio clone() {
		return new SecurityRadio();
	}

    @Override
    protected Console getSpecificConsole(GameData gameData) throws NoSuchThingException {
        return gameData.findObjectOfType(CrimeRecordsConsole.class);
    }

    @Override
    protected Action getSpecificAction(GameData gameData) {
        try {
            return new CrimeRecordsAction(gameData.findObjectOfType(CrimeRecordsConsole.class));
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Cannot get specific action for security radio, no crime console found.");
        }
    }
}
