package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.*;
import model.actions.general.Action;
import model.actions.objectactions.*;
import model.map.rooms.Room;
import model.objects.general.BankUser;
import model.objects.shipments.*;
import util.Pair;

public class RequisitionsConsole extends Console implements BankUser {

    private static Bank bank;
	private List<Shipment> shipments = new ArrayList<>();
    private List<Pair<Actor, Shipment>> history = new ArrayList<>();

    public RequisitionsConsole(Room pos, GameData gameData) {
		super("Requisitions Console", pos);
        shipments.add(new FoodShipment());
        shipments.add(new FireFighterShipment());
		shipments.add(new MedicalShipment());
		shipments.add(new ExterminationShipment());
        shipments.add(new TechnicalShipment());
		shipments.add(new PartyShipment());
        shipments.add(new RobotPartsShipment());
		shipments.add(new MilitaryShipment());
        shipments.add(new WildlifeShipment());
        shipments.add(new ConstructionShipment());
        shipments.add(new EmptyCratesShipment());
	}
	
	@Override
	public void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		Action a = new OrderShipmentAction(this);
		if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
			at.add(a);
		}
		if (cl instanceof Player) {
            at.add(new SitDownAtRequisitionsConsoleAction(gameData, this));
        }

	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("adminconsole", "computer2.png", 1, 12, this);
    }


    public List<Shipment> getShipments() {
		return shipments;
	}

	public void setMoney(int m) {
        bank.setStationMoney(m);

	}
	
	public int getMoney() {
        return bank.getStationMoney();
	}




    public void addHistory(Actor performingClient, Shipment selectedShip) {
        history.add(new Pair<Actor, Shipment>(performingClient, selectedShip));
    }

    public List<Pair<Actor, Shipment>> getHistory() {
        return history;
    }

    @Override
    public void setBankRef(Bank b) {
        bank = b;
    }
}
