package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.*;
import model.actions.general.Action;
import model.actions.objectactions.*;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.CrewCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.rooms.Room;
import model.objects.shipments.*;
import util.Pair;

public class AdministrationConsole extends Console {

    private final Bank bank;

	private List<Shipment> shipments = new ArrayList<>();
    private Map<Actor, Integer> alternateWages = new HashMap<>();
    private Set<Actor> acceptedActors;
    private Set<Actor> toBeDemoted;
    private List<Pair<Actor, Shipment>> history = new ArrayList<>();

    public AdministrationConsole(Room pos, GameData gameData) {
		super("Admin Console", pos);
        acceptedActors = new HashSet<>();
        toBeDemoted = new HashSet<>();
        this.bank = Bank.getInstance(gameData);
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
	}
	
	@Override
	public void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		Action a = new AdminConsoleAction(this);
		if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
			at.add(a);
		}
        if (hasAdminPrivilege(cl)) {
            at.add(new SetWagesAction(this, gameData));

            Action change = new ChangeJobAction(this, gameData);
            if (change.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                at.add(change);
            }
        }
        if (cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter)) {
            at.add(new MarkForDemotionAction(this, gameData));

        }
        at.add(new AcceptNewProfessionAction(this));

	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("adminconsole", "computer2.png", 1, 12, this);
    }

    private boolean hasAdminPrivilege(Actor cl) {
        return GameItem.hasAnItem(cl, new KeyCard());
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

        return getWageForCharacter(a.getInnermostCharacter());


    }

    public static int getWageForCharacter(GameCharacter innermostCharacter) {
        if (innermostCharacter instanceof CaptainCharacter) {
            return 35;
        }
        if (innermostCharacter instanceof VisitorCharacter) {
            return 0;
        }
        if (innermostCharacter instanceof CrewCharacter) {
            return ((CrewCharacter) innermostCharacter).getStartingMoney() / 5;
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

    public Set<Actor> getAcceptedActors() {
        return acceptedActors;
    }

    public Set<Actor> getToBeDemoted() {
        return toBeDemoted;
    }

    public void addHistory(Actor performingClient, Shipment selectedShip) {
        history.add(new Pair<Actor, Shipment>(performingClient, selectedShip));
    }

    public List<Pair<Actor, Shipment>> getHistory() {
        return history;
    }
}
