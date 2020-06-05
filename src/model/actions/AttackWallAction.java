package model.actions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

import java.util.List;

public class AttackWallAction extends Action {
    private final Room targetRoom;
    private String preferredWeapon;

    public AttackWallAction(Room room) {
        super("Attack Wall", SensoryLevel.PHYSICAL_ACTIVITY);
        this.targetRoom = room;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "attacked a wall";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Weapon) {
                opts.addOption(it.getFullName(whosAsking));
            }
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Weapon weapon = null;
        for (GameItem it : performingClient.getItems()) {
            if (it.getFullName(performingClient).equals(preferredWeapon)) {
                weapon = (Weapon)it;
            }
        }
        if (weapon == null) {
            performingClient.addTolastTurnInfo("What? The " + preferredWeapon + " wasn't there? " + failed(gameData, performingClient));
            return;
        }
        performingClient.addTolastTurnInfo("You attacked the wall towards " + targetRoom.getName());
        for (Actor a : targetRoom.getActors()) {
            a.addTolastTurnInfo(weapon.getWallDamageText());
        }
        boolean broken = targetRoom.damageWall(gameData, performingClient.getPosition(), weapon.doWallDamage(gameData, performingClient, targetRoom));
        if (broken) {
            performingClient.addTolastTurnInfo("You broke through the wall!");
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        preferredWeapon = args.get(0);
    }
}
