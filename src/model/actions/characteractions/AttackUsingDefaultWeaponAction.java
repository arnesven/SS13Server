package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.items.general.GameItem;
import model.npcs.NPC;

public class AttackUsingDefaultWeaponAction extends AttackAction {
    public AttackUsingDefaultWeaponAction(NPC npc) {
        super(npc);
        addWithWhat(npc.getCharacter().getDefaultWeapon());
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        performingClient.addItem(performingClient.getCharacter().getDefaultWeapon(), null);
        super.applyTargetingAction(gameData, performingClient, target, item);
        performingClient.getItems().remove(performingClient.getCharacter().getDefaultWeapon());
    }
}
