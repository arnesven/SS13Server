package model.objects.consoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.sprites.Sprite;
import model.Actor;
import model.Bank;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.AdminConsoleAction;
import model.actions.objectactions.SetWagesAction;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.CrewCharacter;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.map.Room;
import model.objects.shipments.*;

public class AdministrationConsole extends Console {

    private final Bank bank;

	private List<Shipment> shipments = new ArrayList<>();
    private Map<Actor, Integer> alternateWages = new HashMap<>();
	
	public AdministrationConsole(Room pos, GameData gameData) {
		super("Admin Console", pos);
        this.bank = Bank.getInstance(gameData);
        shipments.add(new FireFighterShipment());
		shipments.add(new MedicalShipment());
		shipments.add(new ExterminationShipment());
        shipments.add(new TechnicalShipment());
		shipments.add(new PartyShipment());
        shipments.add(new RobotPartsShipment());
		shipments.add(new MilitaryShipment());
        shipments.add(new WildlifeShipment());
	}
	
	@Override
	public void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		Action a = new AdminConsoleAction(this);
		if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
			at.add(a);
		}
        if (canSetWages(cl)) {
            at.add(new SetWagesAction(this, gameData));
        }
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("adminconsole", "computer2.png", 1, 12);
    }

    private boolean canSetWages(Actor cl) {
        return cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof CaptainCharacter) ||
                cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof HeadOfStaffCharacter);
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

    public int getWageForActor(Actor a) {
        if (alternateWages.containsKey(a)) {
            return alternateWages.get(a);
        }

        if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof VisitorCharacter)) {
            return 0;
        }
        if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof CaptainCharacter)) {
            return 35;
        }
        if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof ChangelingCharacter)) {
            return 0;
        }
        if (a.getInnermostCharacter() instanceof CrewCharacter) {
            return ((CrewCharacter)a.getInnermostCharacter()).getStartingMoney() / 5;
        }
        return 0;
    }

    public boolean canPayAllWages(GameData gameData) {
        int sum = 0;
        for (Actor a : gameData.getActors()) {
            sum += getWageForActor(a);
        }
        return sum < bank.getStationMoney();
    }

    public void subtractFromBudget(int amount) {
        bank.subtractFromStationMoney(amount);

    }

    public void setWageForActor(Actor selectedActor, int newWage) {
        alternateWages.put(selectedActor, newWage);
    }
}
