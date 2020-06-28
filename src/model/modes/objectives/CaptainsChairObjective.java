package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.objects.decorations.CaptainsBridgeChair;
import model.objects.general.BioScanner;

public class CaptainsChairObjective extends PirateCaptainTraitorObjective {

    private Locatable locatable;

    public CaptainsChairObjective(GameData gameData) {
        super(gameData);
        try {
            this.locatable = gameData.findObjectOfType(CaptainsBridgeChair.class);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String thingToDetach() {
        return "Captain's Chair";
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }
}
