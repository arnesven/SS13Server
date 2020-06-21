package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Target;
import model.combat.DefaultUnarmedAttack;
import model.combat.MartialArtsAttack;
import model.items.general.GameItem;
import sounds.Sound;
import util.MyRandom;

/**
 * Created by erini02 on 18/12/16.
 */
public class UnarmedAttack extends Weapon implements BludgeoningWeapon {
    private MartialArtsAttack attackType;

    public UnarmedAttack() {
        super("Unarmed Attack", 0.5, 0.5, false, 0.0, true, 0);
        attackType = new DefaultUnarmedAttack();
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        if (MyRandom.nextDouble() > 0.5) {
            return new Sound("punch2");
        }
        return new Sound("punch1");
    }

    @Override
    public boolean doAttack(Actor performingClient, Target target, GameData gameData) {
        attackType.doBeforeAttack(performingClient, target, gameData, this);
        boolean result = super.doAttack(performingClient, target, gameData);
        attackType.doAfterAttack(performingClient, target, gameData, this, result);
        return result;
    }

    @Override
    public GameItem clone() {
        return new UnarmedAttack();
    }

    @Override
    public boolean hasMissSound() {
        return true;
    }

    @Override
    public boolean isMeleeWeapon() {
        return true;
    }

    public void setSelectedAttack(MartialArtsAttack selected) {
        this.attackType = selected;
    }

    @Override
    public boolean givesAttackOfOpportunity() {
        return attackType.givesAttackOfOpportunity();
    }
}
