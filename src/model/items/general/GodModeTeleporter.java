package model.items.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class GodModeTeleporter extends Teleporter {

    public GodModeTeleporter() {
        setName("God Mode Teleporter");
    }

    @Override
    public void useOnce() {
        // do not decrease uses...
    }

    @Override
    protected void addMarkCoordinatesAction(GameData gameData, ArrayList<Action> at, Actor cl) {
        at.add(new Action("Set Destination", SensoryLevel.OPERATE_DEVICE) {
            private Room selected;

            @Override
            protected String getVerb(Actor whosAsking) {
                return "Fiddled with teleporter";
            }

            @Override
            public ActionOption getOptions(GameData gameData, Actor whosAsking) {
                ActionOption opts = super.getOptions(gameData, whosAsking);
                for (String lev : gameData.getMap().getLevels()) {
                    ActionOption opt = new ActionOption(lev);
                    for (Room r : gameData.getMap().getRoomsForLevel(lev)) {
                        opt.addOption(r.getName());
                    }
                    opts.addOption(opt);
                }

                return opts;
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                setMarked(selected);
                performingClient.addTolastTurnInfo("You set the destination to: '" + selected.getName() + "'");
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {
                for (String lev : gameData.getMap().getLevels()) {
                    ActionOption opt = new ActionOption(lev);
                    for (Room r : gameData.getMap().getRoomsForLevel(lev)) {
                        if (args.get(1).contains(r.getName())) {
                            selected = r;
                        }
                    }

                }
            }
        });
    }
}
