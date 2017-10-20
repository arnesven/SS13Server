package model.characters.crew;

import model.Actor;
import model.GameData;
import model.actions.characteractions.ImitateAction;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import model.items.foods.SpaceBurger;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Tools;
import model.items.weapons.Crowbar;
import util.MyRandom;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class StaffAssistantCharacter extends CrewCharacter {

    private static List<GameItem> staffAssistantPossibleStartingItems = makeItemList();
    private List<Action> learned = new ArrayList<>();

    public StaffAssistantCharacter() {
        super("Staff Assistant", 12, 1.0);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return getCrewSpecificItems();
    }

    @Override
    public List<GameItem> getCrewSpecificItems() {
        List<GameItem> gis = new ArrayList<>();
        gis.add(MyRandom.sample(staffAssistantPossibleStartingItems));
        gis.add(MyRandom.sample(staffAssistantPossibleStartingItems));

        return gis;
    }

    @Override
    public GameCharacter clone() {
        return new StaffAssistantCharacter();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (otherActorInRoom()) {
            at.add(new ImitateAction(getActor()));
        }

        for (Action a : learned) {
            if (a.getClass().getConstructors()[0].getParameterCount() == 0) {
                try {
                    at.add((Action)(a.getClass().getConstructors()[0].newInstance()));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean otherActorInRoom() {
        for (Actor a : getPosition().getActors()) {
            if (a != getActor() && !a.isDead()) {
                return true;
            }
        }
        return false;
    }

    private static List<GameItem> makeItemList() {
        List<GameItem> gis = new ArrayList<>();
        gis.add(new Crowbar());
        gis.add(new Tools());
        gis.add(new FireExtinguisher());
        gis.add(new SpaceBurger(null));
        return gis;
    }

    public void addLearnedActions(List<Action> acts) {
        learned.addAll(acts);
    }

}
