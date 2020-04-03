package model.characters.visitors;

import model.Actor;
import model.GameData;
import model.actions.general.AttackAction;
import model.characters.crew.CaptainCharacter;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class CaptainsDaughter extends VisitorCharacter {
    private boolean triggered = false;

    public CaptainsDaughter() {
        super("Captain's Daughter", 0, 1.0);
        this.setGender("woman");
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new CaptainsDaughter();
    }

    @Override
    public int getSize() {
        return SMALL_SIZE;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        Actor a = null;
        try {
            a = findCaptain(gameData);
            if (isDead() && !triggered) {
                triggered = true;

                a.addTolastTurnInfo("Your daughter is dead! You are overpowered by grief!");
                AttackAction atk = new AttackAction(a);
                List<String> args = new ArrayList<String>();
                Weapon weaponToUse = getWeapon(a);
                atk.addWithWhat(weaponToUse);
                args.add("Yourself");
                args.add(weaponToUse.getFullName(a));
                atk.setArguments(args, a);
                atk.printAndExecute(gameData);

            } else if (!triggered) {
                a.addTolastTurnInfo("Your daughter is somewhere on the station.");
            }

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }


    }

    private Actor findCaptain(GameData gameData) throws NoSuchThingException {
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CaptainCharacter)) {
                return a;

            }
        }
        throw new NoSuchThingException("No captain found!");
    }

    private Weapon getWeapon(Actor actor) {
        for (GameItem it : actor.getItems()) {
            if (it instanceof Weapon) {
                if (((Weapon)it).isReadyToUse()) {
                    return (Weapon)it;
                }
            }
        }

        return actor.getCharacter().getDefaultWeapon();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {

    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return false;
    }
}
