package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
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
    protected String thingToDetach() {
        return "Monolith from its rig";
    }


}
