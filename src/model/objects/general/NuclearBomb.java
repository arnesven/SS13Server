package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.NukeSetEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.map.rooms.Room;
import model.map.rooms.RoomType;

import java.util.ArrayList;
import java.util.List;

public class NuclearBomb extends GameObject {

	public NuclearBomb(Room position) {
		super("Nuclear Bomb", position);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("nuclearbomb", "stationobjs.png", 98, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (GameItem.hasAnItem(cl, new NuclearDisc(gameData, false))) {
            at.add(new SetOffNukeAction(this));
        }
    }

    public void detonate(GameData gameData) {
        String level = "ss13";
        try {
            level = gameData.getMap().getLevelForRoom(this.getPosition());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        for (Room r : gameData.getMap().getRoomsForLevel(level)) {
            if (r.getType() != RoomType.hidden) {
                for (Actor a : r.getActors()) {
                    a.getCharacter().beExposedTo(null, new NuclearExplosiveDamage());
                }
                for (GameObject ob : r.getObjects()) {
                    if (ob instanceof BreakableObject) {
                        ((BreakableObject) ob).beExposedTo(null, new NuclearExplosiveDamage());
                    }
                }
            }
        }




    }

    public boolean deactivated() {
        return false;
    }

    private class SetOffNukeAction extends Action {
        private final NuclearBomb nuke;

        public SetOffNukeAction(NuclearBomb nuclearBomb) {
            super("Activate Nuke", SensoryLevel.OPERATE_DEVICE);
            this.nuke = nuclearBomb;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Activated a nuke";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (GameItem.hasAnItem(performingClient, new NuclearDisc(gameData, false))) {
                gameData.addEvent(new NukeSetEvent(NuclearBomb.this));
            } else {
                performingClient.addTolastTurnInfo("What, the nuclear disc wasn't there? " + Action.FAILED_STRING);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
