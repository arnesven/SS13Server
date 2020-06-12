package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;
import model.map.Architecture;
import model.map.doors.Door;
import model.map.rooms.Room;
import sounds.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CastTeleportAction extends CastSpellAction {
    private final TeleportSpellBook spellBook;
    private final Actor forWhom;
    private final GameData gameData;

    public CastTeleportAction(TeleportSpellBook spellBook, GameData gameData, Actor forWhom) {
        super(spellBook, forWhom);
        this.spellBook = spellBook;
        this.forWhom = forWhom;
        this.gameData = gameData;
        addTarget(forWhom.getAsTarget());
    }

    @Override
    public Sound getRealSound() {
        return new Sound("bamf");
    }

    @Override
    protected boolean canBeTargetedBySpell(Target target2) {
        return target2 == forWhom;
    }

    private List<Room> getAdjacentRooms(Actor forWhom) {
        List<Room> list = new ArrayList<>();

        try {
            Architecture arch = new Architecture(gameData.getMap(), gameData.getMap().getLevelForRoom(forWhom.getPosition()).getName(),
                    forWhom.getPosition().getZ());

            Rectangle area = new Rectangle(forWhom.getPosition().getX()-2, forWhom.getPosition().getY()-2,
                    forWhom.getPosition().getWidth()+4, forWhom.getPosition().getHeight()+4);

            for (Room r : arch.getRoomsWithin(area)) {
                list.add(r);
            }

        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean isOkToCast(Actor forWhom, GameData gameData) {
        return getOptions(gameData, forWhom).getSuboptions().get(0).numberOfSuboptions() > 0;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Room r : getAdjacentRooms(whosAsking)) {
            opts.getSuboptions().get(0).addOption(r.getName());
        }
        return opts;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        super.setArguments(args, performingClient);
        for (Room r : getAdjacentRooms(performingClient)) {
            if (args.get(1).equals(r.getName())) {
                spellBook.setTeleportTargetRoom(r);
            }
        }
    }

}
