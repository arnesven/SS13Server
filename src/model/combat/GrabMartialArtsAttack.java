package model.combat;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.itemactions.CancelAction;
import model.characters.general.GameCharacter;
import model.items.weapons.UnarmedAttack;

public class GrabMartialArtsAttack extends MartialArtsAttack {
    private double oldDmg;
    private double oldHit;

    public GrabMartialArtsAttack() {
        super("Grab");
    }

    @Override
    public String getDescription() {
        return getGrabDescription();
    }

    @Override
    public void doBeforeAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack) {
        oldDmg = unarmedAttack.getDamage();
        unarmedAttack.setDamage(getDamage());
        oldHit = unarmedAttack.getHitChance();
        unarmedAttack.setHitChance(getHitChance());
    }

    @Override
    public void doAfterAttack(Actor performingClient, Target target, GameData gameData, UnarmedAttack unarmedAttack, boolean wasAHit) {
        if (target instanceof Actor) {
            Actor opponent = (Actor)target;
            if (isGrabbed(opponent)) {
                performingClient.addTolastTurnInfo("You tried grabbing, but " + opponent.getPublicName(performingClient) +
                        " is already grabbed by somebody else.");
            } else {
                setGrabbed(opponent, performingClient, gameData);
                performingClient.addTolastTurnInfo("You grabbed " + opponent.getPublicName(performingClient) + "!");
                opponent.addTolastTurnInfo("You were grabbed by " + performingClient.getPublicName(opponent) + "!");
            }

        } else {
            if (wasAHit) {
                performingClient.addTolastTurnInfo("You grabbed the " + target.getName() + "...");
            }
        }

        unarmedAttack.setDamage(oldDmg);
        unarmedAttack.setHitChance(oldHit);
    }

    @Override
    protected double getDamage() {
        return 0;
    }

    @Override
    protected double getHitChance() {
        return 0.75;
    }

    private static void setGrabbed(Actor opponent, Actor performingClient, GameData gameData) {
        if (opponent instanceof Player) {
            ((Player) opponent).setNextAction(new CancelAction());
        }
        opponent.setCharacter(new GrabbedDecorator(opponent.getCharacter(), performingClient, gameData.getRound()));
        performingClient.setCharacter(new GrabbingSomeoneDecorator(performingClient.getCharacter(), opponent, gameData.getRound()));
    }

    private static boolean isGrabbed(Actor opponent) {
        return opponent.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof GrabbedDecorator);
    }


    static String getGrabDescription() {
        return "An attempt to grab and hold your opponent for one turn. Deals no damage but, has a chance to disable your opponent " +
                "and makes him or her more vulnerable to attacks. Grabbed (and grabbing) players can be looted by others.";
    }
}
