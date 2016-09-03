package model.actions.general;

import model.Actor;
import model.GameData;

import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class FartWrapperAction extends Action {

    private final Action inner;

    /**
     * @param name   the name of this action
     * @param senses if the action is stealthy it will not be displayed to other players standing in that room.
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
        inner.execute(gameData, performingClient);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        return inner.getOptions(gameData, whosAsking);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        inner.setArguments(args, performingClient);
    }
}
