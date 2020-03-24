package model.items;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.Locator;
import model.items.general.NuclearDisc;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class OperativeLocator extends Locator {

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        if (getTarget() == null) {
            at.add(new Action("Activate", SensoryLevel.OPERATE_DEVICE) {
                @Override
                protected String getVerb(Actor whosAsking) {
                    return "Fiddled with Locator";
                }

                @Override
                protected void execute(GameData gameData, Actor performingClient) {
                    for (Room r : gameData.getAllRooms()) {
                        for (GameItem i : r.getItems()) {
                            if (i instanceof NuclearDisc) {
                                setTarget(i);
                                performingClient.addTolastTurnInfo("Nuclear Disc found in " + r.getName());
                                break;
                            }
                        }
                        for (Actor a : r.getActors()) {
                            for (GameItem i : a.getItems()) {
                                if (i instanceof NuclearDisc) {
                                    setTarget(i);
                                    performingClient.addTolastTurnInfo("Nuclear Disc found in " + r.getName());
                                    break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void setArguments(List<String> args, Actor performingClient) {

                }
            });
        }

    }
}
