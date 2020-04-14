package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.RemoteAccessAction;
import model.actions.general.SensoryLevel;

public class AIRemoteAccessAction extends RemoteAccessAction  {
    public AIRemoteAccessAction() {
        super(SensoryLevel.NO_SENSE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "";
    }


}
