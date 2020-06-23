package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;

import java.util.List;

public class LabelAction extends Action {
    public LabelAction(String s) {
        super(s, SensoryLevel.NO_SENSE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }

    @Override
    public boolean wasPerformedAsQuickAction() {
        return true;
    }
}
