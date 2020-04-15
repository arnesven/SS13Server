package model.actions.objectactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.CaptainCharacter;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
import model.objects.consoles.RequisitionsConsole;
import model.objects.shipments.Shipment;
import util.MyRandom;

public class OrderShipmentAction extends ConsoleAction {


	private RequisitionsConsole pc;
	private Shipment selectedShip;
    private boolean checkedfunds;

    public OrderShipmentAction(RequisitionsConsole console) {
		super("Order Shipment", SensoryLevel.OPERATE_DEVICE);
		this.pc = console;
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		opt.addOption("Station funds $$ " + pc.getMoney());
		for (Shipment ship : pc.getShipments()) {
			if (ship.getCost() <= pc.getMoney() && rankOk(ship, whosAsking)) {
				opt.addOption(ship.getName() + " ($$ " + String.format("%.1f", ship.getCost()/1000.0) + "k)");
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
        if (args.get(0).contains("Station funds $$")) {
            checkedfunds = true;
        }
		for (Shipment ship : pc.getShipments()) {
			if (args.get(0).contains(ship.getName())) {
				selectedShip = ship;
			}
		}
	}

	

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
        if (!checkedfunds) {
            Room targetRoom = null;
            try {
                targetRoom = gameData.getRoom("Cargo Bay");
            } catch (NoSuchThingException e) {
                targetRoom = MyRandom.sample(gameData.getRooms());
            }
            Shipment s = selectedShip.clone();
            pc.setMoney(pc.getMoney() - s.getCost());
            pc.addHistory(performingClient, selectedShip);
            performingClient.addTolastTurnInfo("You ordered a shipment! It will arrive in the " + targetRoom.getName() + " in 2 turns. Station funds; " + pc.getMoney());
        	gameData.addEvent(new ShipmentArrivesEvent(gameData, targetRoom, s));
        } else {
            performingClient.addTolastTurnInfo("The station's account balance is $$ " + pc.getMoney() + ".");
        }
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Fiddled with Admin Console";
	}


	private class ShipmentArrivesEvent extends Event {
		private final int startRound;
		private final Room targetRoom;
		private final Shipment shipment;
		private boolean remove;

		public ShipmentArrivesEvent(GameData gameData, Room targetRoom, Shipment s) {
			super();
			this.startRound = gameData.getRound();
			this.targetRoom = targetRoom;
			this.shipment = s;
		}

		@Override
		public void apply(GameData gameData) {
			if (gameData.getRound() == startRound+2) {
				targetRoom.addObject(new CrateObject(targetRoom, shipment, gameData));
				remove = true;
			}
		}

		@Override
		public String howYouAppear(Actor performingClient) {
			return "";
		}

		@Override
		public SensoryLevel getSense() {
			return SensoryLevel.NO_SENSE;
		}

		@Override
		public boolean shouldBeRemoved(GameData gameData) {
			return remove;
		}
	}
}
