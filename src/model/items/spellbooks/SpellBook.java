package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.SpriteOverlayDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.WizardCharacter;
import model.events.animation.AnimatedSprite;
import model.items.general.GameItem;

public abstract class SpellBook extends GameItem {
    private final int magickaCost;
    private SpriteOverlayDecorator castingEffectDecorator;

    public SpellBook(String string, int magickaCost) {
        super(string, 0.5, false, 1300);
        this.magickaCost = magickaCost;
    }

    protected abstract void doEarlyEffect(GameData gameData, Actor performingClient, Target target);

    protected abstract void doLateEffect(GameData gameData, Actor performingClient, Target target);

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("spellbook", "library.png", 2, 2, this);
    }

    public abstract String getSpellName();

    public abstract String getMagicWords();

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


}
