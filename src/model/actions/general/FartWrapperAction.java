package model.actions.general;

import model.Actor;
import model.GameData;
import sounds.FartSound;
import sounds.Sound;

import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class FartWrapperAction extends Action {

    private final Action inner;

    /**
     */
    public FartWrapperAction(Action inner) {
        super(inner.getName(), inner.getSense());
        this.inner = inner;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "farted";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        inner.doTheAction(gameData, performingClient);
        performingClient.addTolastTurnInfo("You farted.");
       // inner.execute(gameData, performingClient);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        return inner.getOptions(gameData, whosAsking);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        inner.setArguments(args, performingClient);
    }

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new FartSound();
    }
}
