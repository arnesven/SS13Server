package model.characters.general;

import model.GameData;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class WizardCharacter extends HumanCharacter {
    private static final int MAGICKA_MAX = 60;
    private int magicka;

    public WizardCharacter(Integer startRoomID) {
        super("Wizard", startRoomID, 17.0);
        this.magicka = MAGICKA_MAX;
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

    public void removeFromMagicka(int magickaCost) {
        this.magicka -= magickaCost;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        magicka = Math.min(MAGICKA_MAX, magicka + 10);
    }

    public int getMagicka() {
        return magicka;
    }
}
