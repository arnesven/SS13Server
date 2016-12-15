package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.ActorsAshes;
import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class MailBox extends ElectricalMachinery {

    private int points = 0;

    public MailBox(Room room) {
        super("Mailbox", room);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (GameItem.hasAnItem(cl, new ActorsAshes(cl))) {
            at.add(new SendAshesToRelatives());
        }
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("mailbox", "stationobjs.png", 64);
    }

    private class SendAshesToRelatives extends Action {

        private static final int POINTS_PER_SENT = 50;

        public SendAshesToRelatives() {
            super("Send Ashes to Relatives", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with Mailbox";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            List<ActorsAshes> ashes = new ArrayList<>();
            for (GameItem it : performingClient.getItems()) {
                if (it instanceof ActorsAshes) {
                    ashes.add((ActorsAshes)it);
                }
            }
            
            points += ashes.size() * POINTS_PER_SENT;
            for (ActorsAshes ash : ashes) {
                performingClient.addTolastTurnInfo("You sent " +
                        ash.getPublicName(performingClient) + " home.");
                performingClient.getItems().remove(ash);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    public int getPoints() {
        return points;
    }
}
