package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.modes.WizardGameMode;

public class MissionDetails extends GameItem {
    private final WizardGameMode wizGameMode;

    public MissionDetails(WizardGameMode wizardGameMode) {
        super("Mission Details", 0.001, false, 0);
        this.wizGameMode = wizardGameMode;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("missiondetailsnotes", "items.png", 38, 0, this);
    }

    @Override
    public GameItem clone() {
        return new MissionDetails(wizGameMode);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Your objective is: " + wizGameMode.getObjectives().get(wizGameMode.getWizard()).getText();
    }
}
