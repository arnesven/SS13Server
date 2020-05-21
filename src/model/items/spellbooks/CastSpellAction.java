package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.SpellCastingDecorator;
import model.characters.general.WizardCharacter;
import model.items.general.GameItem;

import java.util.List;

public abstract class CastSpellAction extends TargetingAction {
    private final SpellBook spellBook;

    public CastSpellAction(SpellBook spellBook, Actor caster) {
        super("Cast " + spellBook.getSpellName(), SensoryLevel.SPEECH, caster);
        this.spellBook = spellBook;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "said: " + spellBook.getMagicWords();
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return canBeTargetedBySpell(target2);
    }

    protected abstract boolean canBeTargetedBySpell(Target target2);

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (!performingClient.getItems().contains(spellBook)) {
            performingClient.addTolastTurnInfo("What the spell book wasn't there? " + failed(gameData, performingClient));
            return;
        }

        spellBook.beginCasting(gameData, performingClient, target);
        performingClient.addTolastTurnInfo("You start casting " + spellBook.getSpellName() + ": " + spellBook.getMagicWords());
        gameData.executeAtEndOfRound(performingClient, this);
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        super.lateExecution(gameData, performingClient);
        spellBook.endCasting(gameData, performingClient, target);
    }

    public boolean isOkToCast(Actor forWhom, GameData gameData) {
        if (getTargets().isEmpty()) {
            return false;
        }
        if (forWhom.getInnermostCharacter() instanceof WizardCharacter) {
            return ((WizardCharacter)forWhom.getInnermostCharacter()).getMagicka() >= spellBook.getMagickaCost();
        }

        return true;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        super.setArguments(args, performingClient);
        CharacterDecorator castingEffectDecorator = new SpellCastingDecorator(performingClient.getCharacter(), spellBook);
        for (Player p : performingClient.getPosition().getClients()) {
            p.refreshClientData();
        }
        performingClient.setCharacter(castingEffectDecorator);

    }
}
