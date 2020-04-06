package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.general.PutOnAction;
import model.items.general.GameItem;
import model.items.suits.Equipment;
import model.items.suits.MinerSpaceSuit;

import java.util.ArrayList;
import java.util.List;

public class PutOnSuitBehavior implements ActionBehavior {
    private final Class<MinerSpaceSuit> suitClass;

    public PutOnSuitBehavior(Class<MinerSpaceSuit> suitClass) {
        this.suitClass = suitClass;
    }

    @Override
    public void act(Actor npc, GameData gameData) {
        if (!suitClass.isInstance(npc.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT)) && GameItem.hasAnItemOfClass(npc, suitClass)) {
            PutOnAction poa = new PutOnAction(npc);
            List<String> str = new ArrayList<>();
            for (GameItem it : npc.getCharacter().getItems()) {
                if (suitClass.isInstance(it)) {
                    str.add(it.getPublicName(npc));
                    break;
                }
            }
            poa.setArguments(str, npc);
            poa.doTheAction(gameData, npc);
        }
    }
}
