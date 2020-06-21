package model.combat;

import model.Actor;
import model.GameData;
import model.Target;
import model.items.weapons.UnarmedAttack;

import java.io.Serializable;

public abstract class MartialArtsAttack implements Serializable {
    private final String name;

    public MartialArtsAttack(String name) {
        this.name = name;
    }

    public static MartialArtsAttack factory(String s) {
        if (s.contains("harm")) {
            return new ImprovedUnarmedAttack();
        } else if (s.contains("grab")) {
            return new GrabMartialArtsAttack();
        } else if (s.contains("disarm")) {
            return new DisarmMartialArtsAttack();
        }
        throw new IllegalStateException();
    }

    public String getName() {
        return name;
    }

    public abstract String getDescription();

    public boolean givesAttackOfOpportunity() {
        return false;
    }

    public abstract void doBeforeAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack);

    public abstract void doAfterAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack, boolean wasAHit);

    public String getHitAndDamage() {
        return "<b>Hit Chance: </b>" + ((int)(getHitChance()*100.0)) + "% <b>Damage:</b> " + getDamage();
    }

    protected abstract double getDamage();

    protected abstract double getHitChance();
}
