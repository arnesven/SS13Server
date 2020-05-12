package model.items.general;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CrimeRecordsAction;
import model.actions.objectactions.ReportCrimeAction;
import model.items.NoSuchThingException;
import model.objects.consoles.Console;
import model.objects.consoles.CrimeRecordsConsole;

import java.util.ArrayList;
import java.util.List;

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
    protected List<Action> getSpecificActions(GameData gameData) {
        try {
            List<Action> res = new ArrayList<>();
            res.add(new CrimeRecordsAction(gameData.findObjectOfType(CrimeRecordsConsole.class)));
            return res;
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Cannot get specific action for security radio, no crime console found.");
        }
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Can be used to report crimes remotely. A connection to the Crime Records Console is needed however.";
    }
}
