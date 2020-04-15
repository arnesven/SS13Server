package model.objects.consoles;

import model.Actor;
import model.Bank;
import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.AcceptNewProfessionAction;
import model.actions.objectactions.ChangeJobAction;
import model.actions.objectactions.MarkForDemotionAction;
import model.actions.objectactions.SetWagesAction;
import model.characters.crew.CaptainCharacter;
import model.characters.crew.CrewCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.rooms.OfficeRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.*;

public class PersonnelConsole extends Console {

    private final Bank bank;

    private Map<Actor, Integer> alternateWages = new HashMap<>();
    private Set<Actor> acceptedActors;
    private Set<Actor> toBeDemoted;


    public PersonnelConsole(Room r, GameData gameData) {
        super("Personnel Console", r);
        acceptedActors = new HashSet<>();
        toBeDemoted = new HashSet<>();
        this.bank = Bank.getInstance(gameData);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
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

    private boolean hasAdminPrivilege(Actor cl) {
        return GameItem.hasAnItem(cl, new KeyCard());
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


    public void setWageForActor(Actor selectedActor, int newWage) {
        alternateWages.put(selectedActor, newWage);
    }

    public Set<Actor> getAcceptedActors() {
        return acceptedActors;
    }

    public Set<Actor> getToBeDemoted() {
        return toBeDemoted;
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

}
