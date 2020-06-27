package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.map.levels.NewAlgiersMapLevel;
import model.objects.monolith.MonolithExperimentRig;

public class MonolithObjective extends PirateCaptainTraitorObjective {
    private Locatable locatable;

    public MonolithObjective(GameData gameData) {
        super(gameData);
        try {
            MonolithExperimentRig rig = gameData.findObjectOfType(MonolithExperimentRig.class);
            this.locatable = rig.getCosmicMonolith();
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public Locatable getLocatable() {
        return locatable;
    }

    @Override
    public String getText() {
        return "Detach the Monolith from its rig (in " + locatable.getPosition().getName() +
                ") and bring it back to your pirate stronghold (New Algiers). " +
                super.DETACH_INFO;
    }




}
