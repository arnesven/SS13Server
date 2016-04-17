package model.items.general;

import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.ReportCrimeAction;
import model.objects.consoles.CrimeRecordsConsole;

public class SecurityRadio extends Radio {


	public SecurityRadio() {
		super("Sec Radio");
	}
	

	@Override
	public SecurityRadio clone() {
		return new SecurityRadio();
	}

    @Override
    protected Action getSpecificAction(GameData gameData) {
        return new ReportCrimeAction(CrimeRecordsConsole.find(gameData));
    }
}
