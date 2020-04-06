package model.modes.goals;

import model.GameData;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.ChilledDecorator;
import model.characters.general.GameCharacter;
import model.items.suits.Equipment;
import model.items.suits.SunGlasses;

public class BeColdGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "You're cool! Put on some sunglasses and get cold.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getCharacter().getEquipment().getEquipmentForSlot(Equipment.HEAD_SLOT) instanceof SunGlasses &&
                getBelongsTo().getCharacter().checkInstance((GameCharacter cd) -> cd instanceof ChilledDecorator);
    }
}
