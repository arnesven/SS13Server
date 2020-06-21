package model.combat;

import model.Actor;
import model.GameData;
import model.Target;
import model.items.weapons.UnarmedAttack;

public class DefaultUnarmedAttack extends MartialArtsAttack {
    public DefaultUnarmedAttack() {
        super("Default");
    }

    @Override
    public String getDescription() {
        return getHarmDescription();
    }

    @Override
    public boolean givesAttackOfOpportunity() {
        return true;
    }

    @Override
    public void doBeforeAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack) {

    }

    @Override
    public void doAfterAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack, boolean wasAHit) {

    }

    @Override
    protected double getDamage() {
        return 0.5;
    }

    @Override
    protected double getHitChance() {
        return 0.5;
    }

    static String getHarmDescription() {
        return "A normal punch, slap or jab. Low hit chance and damage but will always \"pin\" your opponent. " +
                "Pinned opponents can be looted by others and are exposed to an attack of opportunity if they try to move from the room.";
    }
}
