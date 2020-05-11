package model.actions.general;

import model.Actor;
import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class MultiAction extends Action {

    private String descr;
    private List<Action> inners;

    public MultiAction(String name) {
        super(name, SensoryLevel.NO_SENSE);
        inners = new ArrayList<>();
        descr = null;
    }

    public MultiAction(String name, String descr) {
        this(name);
        this.descr = descr;
    }

    public void addAction(Action a) {
        inners.add(a);
        setSense(a.getSense());
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return descr;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (Action a : inners) {
            a.doTheAction(gameData, performingClient);
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        // Should not be called, arguments should be set on the composing actions
    }
}
