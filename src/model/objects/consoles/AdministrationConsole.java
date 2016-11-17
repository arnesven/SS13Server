package model.objects.consoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actor;
import model.GameData;
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

	private int money = 14000;
	private List<Shipment> shipments = new ArrayList<>();
    private Map<Actor, Integer> alternateWages = new HashMap<>();
	
	public AdministrationConsole(Room pos) {
		super("Admin Console", pos);
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

    private boolean canSetWages(Actor cl) {
        return cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof CaptainCharacter) ||
                cl.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof HeadOfStaffCharacter);
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
        if (a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof CrewCharacter)) {
            return ((CrewCharacter)a.getInnermostCharacter()).getStartingMoney() / 5;
        }
        return 0;
    }

    public boolean canPayAllWages(GameData gameData) {
        int sum = 0;
        for (Actor a : gameData.getActors()) {
            sum += getWageForActor(a);
        }
        return sum < money;
    }

    public void subtractFromBudget(int amount) {
        money -= amount;
    }

    public void setWageForActor(Actor selectedActor, int newWage) {
        alternateWages.put(selectedActor, newWage);
    }
}
