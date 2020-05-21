package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.*;
import model.items.general.GameItem;
import model.items.spellbooks.SpellBook;

import java.util.List;

public class QuickCastWand extends WandWeapon {
    private SpellBook selectedSpell;

    public QuickCastWand() {
        super("Quick Cast Wand", 0.0);
        this.selectedSpell = null;
    }

    private void setSelectedSpell(SpellBook selectedSpellBook) {
        this.selectedSpell = selectedSpellBook;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("quickcastwand", "weapons2.png", 9, 26, this);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);
        if (selectedSpell != null) {
            selectedSpell.doEarlyEffect(gameData, performingClient, target);
            selectedSpell.doLateEffect(gameData, performingClient, target);
        } else {
            performingClient.addTolastTurnInfo("Poof! Eh, what... nothing happened?");
        }
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> list = super.getInventoryActions(gameData, forWhom);

        if (forWhom instanceof Player) {
            SetQuickCastSpellAction sqcsa = new SetQuickCastSpellAction(gameData, this);
            if (sqcsa.getOptions(gameData, forWhom).numberOfSuboptions() > 0) {
                list.add(sqcsa);
            }
        }

        return list;
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        if (selectedSpell == null) {
            return super.getExtraDescriptionStats(gameData, performingClient) + "<i>Not Imbued</i>";
        }
        return super.getExtraDescriptionStats(gameData, performingClient) +
                "<b>Imbued With: </b>" + selectedSpell.getSpellName();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A curious wand which can be imbued with the power of other spells. " +
                "Once imbued, the wand will trigger the spell once its wielder uses it to attack the victim. " +
                "Not only does the quick cast wand save the wielder magicka, it also performs the spell immediately " +
                "instead of triggering at the end of the round.";
    }

    private class SetQuickCastSpellAction extends Action {
        private final QuickCastWand wand;
        private final GameData gameData;
        private String spellString;

        public SetQuickCastSpellAction(GameData gameData, QuickCastWand quickCastWand) {
            super("Imbue With Spell", SensoryLevel.OPERATE_DEVICE);
            this.wand = quickCastWand;
            this.gameData = gameData;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Fiddled with " + wand.getPublicName(whosAsking);
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts =  super.getOptions(gameData, whosAsking);
            for (GameItem it :whosAsking.getItems()) {
                if (it instanceof SpellBook) {
                    if (((SpellBook)it).canBeQuickCast()) {
                        opts.addOption(((SpellBook) it).getSpellName());
                    }
                }
            }
            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            SpellBook selectedSpellBook = null;
            for (GameItem it : performingClient.getItems()) {
                if (it instanceof SpellBook) {
                    if (((SpellBook) it).getSpellName().equals(spellString)) {
                        selectedSpellBook = (SpellBook)it;
                    }
                }
            }

            if (selectedSpellBook == null) {
                performingClient.addTolastTurnInfo("What, the spell book was missing? " + failed(gameData, performingClient));
            } else {
                wand.setSelectedSpell(selectedSpellBook);
                performingClient.addTolastTurnInfo("The wand is now imbued with the magic of " + selectedSpell.getSpellName() + ".");
            }

        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            spellString = args.get(0);
        }

    }

}
