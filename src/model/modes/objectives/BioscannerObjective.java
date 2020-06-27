package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.objects.general.BioScanner;

public class BioscannerObjective extends PirateCaptainTraitorObjective {
    private Locatable locatable;

    public BioscannerObjective(GameData gameData) {
        super(gameData);
        try {
            this.locatable = gameData.findObjectOfType(BioScanner.class);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String thingToDetach() {
        return "Bioscanner";
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }


}
