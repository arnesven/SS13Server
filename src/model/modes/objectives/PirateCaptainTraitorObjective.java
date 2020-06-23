package model.modes.objectives;

import model.GameData;
import model.items.general.Locatable;

public class PirateCaptainTraitorObjective implements TraitorObjective {
    @Override
    public boolean wasCompleted() {
        return false;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public Locatable getLocatable() {
        return null;
    }

    @Override
    public String getText() {
        return "TODO: Lorem Ipsum!";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return false;
    }
}
