package model.items;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.objectactions.ReportCrimeAction;
import model.objects.consoles.CrimeRecordsConsole;

public class SecurityRadio extends GameItem {

	private boolean noConnect = false;

	public SecurityRadio() {
		super("Radio", 0.5);
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (noConnect) {
			return super.getFullName(whosAsking) + " (no connection)";
		}
		return super.getFullName(whosAsking);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<model.actions.Action> at, model.Player cl) {
		CrimeRecordsConsole console = CrimeRecordsConsole.find(gameData);
		if (console.isPowered(gameData) && !console.isBroken()) {
			noConnect = false;
			at.add(new ReportCrimeAction(console));
		} else {
			noConnect = true;
		}
	}
	
	@Override
	protected char getIcon() {
		return 'd';
	}

	@Override
	public SecurityRadio clone() {
		return new SecurityRadio();
	}

}
