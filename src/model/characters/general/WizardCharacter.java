package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
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
        magicka = Math.min(MAGICKA_MAX, magicka + getMagickaRegeneration());
        interruptedByAttack = false;
    }

    private int getMagickaRegeneration() {
        return 3;
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

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new ShowManaAction(gameData));
    }

    public void setMagicka(int i) {
        this.magicka = Math.min(MAGICKA_MAX, i);
    }

    private class ShowManaAction extends Action {

        private final GameData gameData;

        public ShowManaAction(GameData gameData) {
            super("Magicka: " + magicka, SensoryLevel.NO_SENSE);
            this.gameData  = gameData;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            // Should not happen
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            gameData.getChat().serverInSay("You have " + magicka + " magicka.", (Player)performingClient);
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }

        @Override
        public boolean doesSetPlayerReady() {
            return false;
        }

        @Override
        public Sprite getAbilitySprite() {
            Sprite base = new Sprite("magicka", "wizardstuff.png", 0, 1, null);
            List<Sprite> sprs = new ArrayList<>();
            sprs.add(base);
            int level = 9 - (int)((magicka * 9.0) / MAGICKA_MAX);
            if (level < 9) {
                sprs.add(new Sprite("mlevel", "wizardstuff.png", level+1, 1, null));
            }
            return new Sprite("magickalev"+level, "human.png", 0, sprs, getActor());
        }
    }
}
