package model.characters.general;

import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class WizardCharacter extends HumanCharacter {
    public WizardCharacter(Integer startRoomID) {
        super("Wizard", startRoomID, 17.0);
    }

    public static String getAntagonistDescription() {
        return "You are an intergalactic hedge wizard with a mission on SS13.";
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public GameCharacter clone() {
        return new WizardCharacter(getStartingRoom());
    }
}
