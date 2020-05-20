package model.characters.general;

import model.Actor;
import model.GameData;
import model.characters.decorators.DisablingDecorator;
import model.characters.decorators.HandCuffedDecorator;
import model.characters.decorators.PinnedDecorator;
import model.characters.decorators.StunnedDecorator;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class WizardCharacter extends HumanCharacter {
    private static final int MAGICKA_MAX = 60;
    private static final double ATTACK_INTERRUPT_CHANCE = 0.5;
    private int magicka;
    private boolean interruptedByAttack;

    public WizardCharacter(Integer startRoomID) {
        super("Wizard", startRoomID, 17.0);
        this.magicka = MAGICKA_MAX;
        interruptedByAttack = false;
    }

    public static String getAntagonistDescription() {
        return "You are an intergalactic hedge wizard with a mission on SS13.";
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new WizardCharacter(getStartingRoom());
    }

    public void removeFromMagicka(int magickaCost) {
        this.magicka -= magickaCost;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        magicka = Math.min(MAGICKA_MAX, magicka + 10);
        interruptedByAttack = false;
    }

    public int getMagicka() {
        return magicka;
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
        boolean succ = super.beAttackedBy(performingClient, weapon, gameData);
        if (succ && MyRandom.nextDouble() < ATTACK_INTERRUPT_CHANCE) {
            interruptedByAttack = true;
            performingClient.addTolastTurnInfo("You interrupted " + getActor().getPublicName(performingClient) + "'s spell.");
        }
        return succ;
    }

    public boolean wasInterrupted() {
        return isDead() || !getsActions() || interruptedByAttack ||
                getActor().getCharacter().checkInstance((GameCharacter gc) ->
                                                            gc instanceof DisablingDecorator) ;
    }
}
