package model.objects.consoles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.objectactions.AdminConsoleAction;
import model.map.Room;
import model.objects.shipments.ExterminationShipment;
import model.objects.shipments.FireFighterShipment;
import model.objects.shipments.MedicalShipment;
import model.objects.shipments.MilitaryShipment;
import model.objects.shipments.PartyShipment;
import model.objects.shipments.Shipment;

public class AdministrationConsole extends Console {

	private int money = 14000;
	private List<Shipment> shipments = new ArrayList<>();
	
	public AdministrationConsole(Room pos) {
		super("Admin Console", pos);
		shipments.add(new FireFighterShipment());
		shipments.add(new MedicalShipment());
		shipments.add(new ExterminationShipment());
		shipments.add(new PartyShipment());
		shipments.add(new MilitaryShipment());
	}
	
	@Override
	public void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		Action a = new AdminConsoleAction(this);
		if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
			at.add(a);
		}
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setMoney(int m) {
		this.money = m;
	}
	
	public int getMoney() {
		return money;
	}

}
