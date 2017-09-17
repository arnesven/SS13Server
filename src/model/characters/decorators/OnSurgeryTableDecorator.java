package model.characters.decorators;

import model.GameData;
import model.characters.general.GameCharacter;
import model.objects.general.SurgeryTable;

/**
 * Created by erini02 on 29/11/16.
 */
public class OnSurgeryTableDecorator extends CharacterDecorator {
    private final SurgeryTable table;

    public OnSurgeryTableDecorator(GameCharacter chara, SurgeryTable table) {
        super(chara, "invisible");
        this.table = table;
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        if (table.getPosition() != getPosition()) {
            table.takePersonOffTable();
            getActor().removeInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator);
        }
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
