package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.SpriteOverlayDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.WizardCharacter;
import model.events.animation.AnimatedSprite;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellBook extends GameItem {
    private final int magickaCost;
    private SpriteOverlayDecorator castingEffectDecorator;

    public SpellBook(String string, int magickaCost) {
        super(string + " Spell Book", 0.5, false, 1300);
        this.magickaCost = magickaCost;
    }

    public static List<SpellBook> getAllSpellBooks() {
        List<SpellBook> sp = new ArrayList<>();       // Mana Cost
        sp.add(new PolymorphSpellBook());             //        13
        sp.add(new SectumsempraSpellBook());          //        15
        sp.add(new ClothesSwapSpellBook());           //         7
        sp.add(new TeleportSpellBook());              //        10
        sp.add(new RaiseDeadSpellBook());             //         9
        sp.add(new OpenDimensionalPortalSpellBook()); //        15
        sp.add(new DetonateSpellBook());              //        15
        sp.add(new InvisibilitySpellBook());          //        11
        sp.add(new RegenerationSpellBook());          //         8
        return sp;
    }

    public abstract void doEarlyEffect(GameData gameData, Actor performingClient, Target target);

    public abstract void doLateEffect(GameData gameData, Actor performingClient, Target target);

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> list = super.getInventoryActions(gameData, forWhom);
        CastSpellAction cast = getCastAction(gameData, forWhom);
        if (cast.isOkToCast(forWhom, gameData)) {
            list.add(cast);
        }

        return list;
    }

    public abstract String getSpellName();

    public abstract String getMagicWords();

    protected abstract CastSpellAction getCastAction(GameData gameData, Actor forWhom);

    protected abstract String getSpellDescription();

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spellbook", "library.png", 2, 2, this);
    }


    public void beginCasting(GameData gameData, Actor performingClient, Target target) {
        if (performingClient.getInnermostCharacter() instanceof WizardCharacter) {
            WizardCharacter wizChar = (WizardCharacter)performingClient.getInnermostCharacter();
            wizChar.removeFromMagicka(this.getMagickaCost());
            doEarlyEffect(gameData, performingClient, target);
        } else {
            performingClient.addTolastTurnInfo("You try to utter the words, but you just sound ridiculous!");
        }
    }


    public Sprite getCastingEffect() {
        return new AnimatedSprite("tingling", "effects.png", 4, 0, 32, 32, null, 10, true);
    }

    protected int getMagickaCost() {
        return magickaCost;
    }

    public void endCasting(GameData gameData, Actor performingClient, Target target) {
        if (performingClient.getInnermostCharacter() instanceof WizardCharacter) {
            if (((WizardCharacter)performingClient.getInnermostCharacter()).wasInterrupted()) {
                performingClient.addTolastTurnInfo("Your spell was interrupted - doh!");
                return;
            }
        }
        doLateEffect(gameData, performingClient, target);
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "<b>Magicka Cost: </b>" + magickaCost;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A spell book containing instructions for casting the spell \"" + getSpellName() +
                "\". Remember, spells take time to caste and are resolved at the end of the round. " + getSpellDescription();
    }

    public boolean canBeQuickCast() {
        return true;
    }

    public boolean isTargetingSpell() {
        return true;
    }
}
