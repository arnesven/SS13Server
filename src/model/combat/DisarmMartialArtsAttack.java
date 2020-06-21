package model.combat;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.items.general.GameItem;
import model.items.weapons.UnarmedAttack;
import model.items.weapons.Weapon;
import model.npcs.NPC;
import util.MyRandom;

import java.util.List;

public class DisarmMartialArtsAttack extends MartialArtsAttack {
    private double oldDamage;
    private double oldHit;

    public DisarmMartialArtsAttack() {
        super("Disarm");
    }

    @Override
    public String getDescription() {
        return getDisarmDescription();
    }

    @Override
    public void doBeforeAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack) {
        oldDamage = unarmedAttack.getDamage();
        oldHit = unarmedAttack.getHitChance();
        unarmedAttack.setDamage(0.0);
        unarmedAttack.setHitChance(getHitChance());
    }

    @Override
    public void doAfterAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack, boolean wasAHit) {
        if (target instanceof Actor) {
            if (wasAHit) {
                if (target instanceof Player) {
                    Action a = ((Player) target).getNextAction();
                    if (a instanceof AttackAction) {
                        GameItem itemUsed = ((AttackAction) a).getItem();
                        disarm((Actor)target, itemUsed, performingClient);
                    }
                } else if (target instanceof NPC) {
                    GameItem w = lookForWeapon(target.getItems());
                    if (w != null) {
                        disarm((Actor)target, w, performingClient);
                    }
                }
            }
        } else {
            if (wasAHit) {
                performingClient.addTolastTurnInfo("You tried to disarm the " + target.getName() + ", but obviously failed.");
            }
        }
        unarmedAttack.setHitChance(oldHit);
        unarmedAttack.setDamage(oldDamage);
    }

    @Override
    protected double getDamage() {
        return 0.0;
    }

    @Override
    protected double getHitChance() {
        return 0.8;
    }

    private void disarm(Actor opponent, GameItem weaponUsed, Actor performingClient) {
        if (opponent.getItems().contains(weaponUsed)) {
            opponent.addTolastTurnInfo("You were disarmed by " + performingClient.getPublicName(opponent) + "!");
            if (MyRandom.nextDouble() < 0.5) {
                String mess = "The " + weaponUsed.getPublicName(performingClient) + " was dropped on the floor!";
                performingClient.addTolastTurnInfo(mess);
                opponent.addTolastTurnInfo(mess);
                opponent.getItems().remove(weaponUsed);
                opponent.getPosition().addItem(weaponUsed);
            } else {
                performingClient.addTolastTurnInfo("You disarmed " + opponent.getPublicName(performingClient) + "!");
                opponent.getItems().remove(weaponUsed);
                performingClient.getCharacter().giveItem(weaponUsed, opponent.getAsTarget());
            }
        }
    }


    private GameItem lookForWeapon(List<GameItem> items) {
        for (GameItem it : items) {
            if (it instanceof Weapon) {
                return it;
            }
        }
        return null;
    }


    private static String getDisarmDescription() {
        return "Disarm your opponent. Players can only lose a weapon they are currently attacking with. " +
                "NPCs can lose their weapon(s) regardless of their actions. Your opponent's weapon may be dropped or transferred immediately to you.";
    }
}
