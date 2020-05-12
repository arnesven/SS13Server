package model.items.laws;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public abstract class AIAbility extends GameItem {
    private final int spriteNum;
    private final boolean requiresOptions;

    public AIAbility(String string, int spriteNum, boolean requiresOptions) {
        super(string, 0.0, false, 0);
        this.spriteNum = spriteNum;
        this.requiresOptions = requiresOptions;
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> actions = new ArrayList<>();
        Action a = getAbilityAction(gameData, forWhom);
        if (!requiresOptions || a.getOptions(gameData, forWhom).numberOfSuboptions() > 0) {
            actions.add(getAbilityAction(gameData, forWhom));
        }
        return actions;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("aiabilitynr" + spriteNum, "module.png", spriteNum, this);
    }

    protected abstract Action getAbilityAction(GameData gameData, Actor forWhom);

    @Override
    public GameItem clone() {
        throw new IllegalStateException("Don't clone AI Abilities!");
    }

    @Override
    public boolean isRecyclable() {
        return false;
    }
}
