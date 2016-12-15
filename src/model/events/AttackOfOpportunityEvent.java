package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

/**
 * Created by erini02 on 16/04/16.
 */
public class AttackOfOpportunityEvent extends Event {
    private Actor attacker;
    private Actor defender;
    private Weapon weapon;
    private Room room;

    public AttackOfOpportunityEvent(Actor performingClient,
                                    Actor target, Weapon w) {
        this.room = performingClient.getPosition();
        this.attacker = performingClient;
        this.defender = target;
        this.weapon = w;
    }

    @Override
    public void apply(GameData gameData) {
        if (this.room == attacker.getPosition() &&
                this.room != defender.getPosition() &&
                !defender.isDead() && !attacker.isDead()) {
            weapon.doAttack(attacker, defender.getAsTarget(), gameData);
            if (defender.isDead()) {
                defender.moveIntoRoom(this.room);
            }
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
        return true; // one time only
    }
}
