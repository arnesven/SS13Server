package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipment implements Serializable {

    public static final int HEAD_SLOT = 0;
    public static final int HANDS_SLOT = 1;
    public static final int TORSO_SLOT = 2;
    public static final int FEET_SLOT = 3;

    private final GameCharacter character;
    private SuitItem[] slots;
    private static final String[] slotNames = new String[]{"Head", "Hands", "Torso", "Feet"};

    public Equipment(GameCharacter gameCharacter) {
        this.character = gameCharacter;
        slots = new SuitItem[4];
    }

    public List<String> getGUIData(GameData gameData, Player player) {
        List<String> result = new ArrayList<>();
        for (int slotIndex = 0; slotIndex < slots.length; slotIndex++) {
            SuitItem w = slots[slotIndex];
            if (w == null) {
                result.add(Sprite.blankSprite().getName() + "<img>" + slotNames[slotIndex] + "<img>" +
                        Action.makeActionListStringSpecOptions(gameData, new ArrayList<>(), player));
            } else {
                result.add(w.howDoYouAppearEquipped(gameData, player));
            }
        }

        return result;
    }

    public SuitItem getEquipmentForSlot(int slot) {
        return slots[slot];
    }

    public void putOnEquipmentInSlot(SuitItem newSuit, int slot) {
        newSuit.setUnder(getEquipmentForSlot(slot));
        slots[slot] = newSuit;
    }

    public void removeEquipmentForSlot(int slot) {
        if (slots[slot] != null) {
            SuitItem underSuit = slots[slot].getUnder();
            slots[slot].setUnder(null);
            slots[slot] = underSuit;
        }
    }

    public Sprite getGetup(Actor whosAsking) {
        Sprite sp;
        if (slots[TORSO_SLOT] == null) {
            sp = character.getActor().getCharacter().getNakedSprite();
        } else {
            sp = slots[TORSO_SLOT].getGetup(character.getActor(), whosAsking);
        }

        return sp;
    }
}
