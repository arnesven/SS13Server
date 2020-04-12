package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

public abstract class FancyFrameAction extends Action {
    public FancyFrameAction(String name, SensoryLevel senses) {
        super(name, senses);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Nothing happens, player did not select a real action...
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
