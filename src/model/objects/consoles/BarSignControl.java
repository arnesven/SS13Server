package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.map.rooms.BarRoom;
import model.map.rooms.Room;
import model.objects.BarSign;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarSignControl extends Console {
    public BarSignControl(Room barRoom) {
        super("Bar Sign Control", barRoom);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new BarSignAction());
    }

    private class BarSignAction extends Action {
        private String selected;


        public BarSignAction() {
            super("Set Bar Sign", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with " + BarSignControl.this.getName();
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            boolean didStuff = false;
            List<Room> roomsToLookAt = new ArrayList<>();
            roomsToLookAt.addAll(BarSignControl.this.getPosition().getNeighborList());
            roomsToLookAt.add(BarSignControl.this.getPosition());
            for (Room r : roomsToLookAt) {
                for (GameObject obj : r.getObjects()) {
                    if (obj instanceof BarSign) {
                        ((BarSign)obj).setAppearance(getSigns().get(selected));
                        didStuff = true;
                    }
                }
            }
            if (didStuff) {
                performingClient.addTolastTurnInfo("You set the bar's signs to '" + selected + "'");
            } else {
                performingClient.addTolastTurnInfo("No bar signs connected.");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            selected = args.get(0);
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            for (String signName : BarSignControl.getSigns().keySet()) {
                ActionOption innerOpt = new ActionOption(signName);

                opts.addOption(innerOpt);
            }
            return opts;
        }
    }

    private static Map<String, Sprite> getSigns() {
        HashMap<String, Sprite> map = new HashMap<>();
        map.put("Armok's Bar N Grill", new Sprite("armoksbarsign1", "barsigns.png", 0, 0, 64, 32, null));
        map.put("The Orchard", new Sprite("theorchardbarsign1", "barsigns.png", 0, 3, 64, 32, null));
        map.put("Mead Bay", new Sprite("meadbaybarsign1", "barsigns.png", 0, 2, 64, 32, null));
        map.put("Whiskey Implant", new Sprite("whiskeybarsign1", "barsigns.png", 0, 4, 64, 32, null));

        return map;
    }
}
