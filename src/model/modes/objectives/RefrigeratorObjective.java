package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.objects.general.Refrigerator;

public class RefrigeratorObjective extends PirateCaptainTraitorObjective {
    private Locatable locatable;

    public RefrigeratorObjective(GameData gameData) {
        super(gameData);
        try {
            this.locatable = gameData.findObjectOfType(Refrigerator.class);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }

    @Override
    protected String thingToDetach() {
        return "Refrigerator";
    }
}
