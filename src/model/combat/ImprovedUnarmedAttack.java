package model.combat;

import model.Actor;
import model.GameData;
import model.Target;
import model.items.weapons.UnarmedAttack;

public class ImprovedUnarmedAttack extends MartialArtsAttack {
    private double oldDamage;
    private double oldHit;

    public ImprovedUnarmedAttack() {
        super("Harm");
    }

    @Override
    public String getDescription() {
        return "A stronger and more precise punch than normal. Will always \"pin\" your opponent. " +
                "Pinned opponents can be looted by others and are exposed to an attack of opportunity if they try to move from the room.";
    }

    @Override
    public void doBeforeAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack) {
        oldDamage = unarmedAttack.getDamage();
        oldHit = unarmedAttack.getHitChance();
        unarmedAttack.setHitChance(getHitChance());
        unarmedAttack.setDamage(getDamage());
    }

    @Override
    public void doAfterAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack, boolean wasAHit) {
        unarmedAttack.setDamage(oldDamage);
        unarmedAttack.setHitChance(oldHit);
    }

    @Override
    protected double getDamage() {
        return 0.75;
    }

    @Override
    protected double getHitChance() {
        return 0.75;
    }
}
