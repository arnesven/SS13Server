package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.CaptainCharacter;
import model.map.Room;
import model.objects.general.CrateObject;
import model.objects.consoles.AdministrationConsole;
import model.objects.shipments.Shipment;

public class AdminConsoleAction extends ConsoleAction {


	private AdministrationConsole pc;
	private Shipment selectedShip;

	public AdminConsoleAction(AdministrationConsole console) {
		super("Admin Console", SensoryLevel.OPERATE_DEVICE);
		this.pc = console;
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		
		for (Shipment ship : pc.getShipments()) {
			if (ship.getCost() <= pc.getMoney() && rankOk(ship, whosAsking)) {
				opt.addOption(ship.getName() + " ($ " + String.format("%.1f", ship.getCost()/1000.0) + "k)");
			}
		}
		
		return opt;
	}

	private boolean rankOk(Shipment s, Actor whosAsking) {
		return whosAsking.getSpeed() >= s.getRankNeeded() &&
				whosAsking.getSpeed() <= (new CaptainCharacter()).getSpeed();
		
	}

	@Override
	public void setArguments(List<String> args, Actor p) { 
		for (Shipment ship : pc.getShipments()) {
			if (args.get(0).contains(ship.getName())) {
				selectedShip = ship;
			}
		}
	}

	

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		Room gate = gameData.getRoom("Shuttle Gate");
		Shipment s = selectedShip.clone();
		gate.addObject(new CrateObject(gate, s));
		pc.setMoney(pc.getMoney() - s.getCost());
		performingClient.addTolastTurnInfo("You ordered a shipment! It has arrived in the Shuttle Gate. Station funds; " + pc.getMoney());
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with Admin Console";
	}


}
