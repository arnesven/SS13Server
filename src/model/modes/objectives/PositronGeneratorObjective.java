package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.Locatable;
import model.objects.decorations.CaptainsBridgeChair;
import model.objects.power.PositronGenerator;

public class PositronGeneratorObjective extends PirateCaptainTraitorObjective {
    private Locatable locatable;

    public PositronGeneratorObjective(GameData gameData) {
        super(gameData);
        try {
            this.locatable = gameData.findObjectOfType(PositronGenerator.class);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String thingToDetach() {
        return "Positron Generator";
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }
}
