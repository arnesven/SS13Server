package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.map.levels.NewAlgiersMapLevel;
import model.objects.monolith.MonolithExperimentRig;


public class PirateCaptainTraitorObjective implements TraitorObjective {
    private final GameData gameData;
    private Locatable locatable;

    public PirateCaptainTraitorObjective(GameData gameData) {
        this.gameData = gameData;
        try {
            MonolithExperimentRig rig = gameData.findObjectOfType(MonolithExperimentRig.class);
            this.locatable = rig.getCosmicMonolith();
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean wasCompleted() {
        return isCompleted(gameData);
    }

    @Override
    public int getPoints() {
        return 500;
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }

    @Override
    public String getText() {
        return "Detach the Monolith from its rig (in " + locatable.getPosition().getName() + ") and bring it back to your pirate stronghold (New Algiers). " +
                "(Detaching objects requires Blow Torch or Laser Sword).";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            return gameData.getMap().getLevelForRoom(locatable.getPosition()).getName().equals(NewAlgiersMapLevel.LEVEL_NAME);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
            return false;
        }

    }
}
