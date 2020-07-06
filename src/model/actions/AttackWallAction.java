package model.actions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import sounds.Sound;

import java.util.List;

public class AttackWallAction extends Action {
    private final Room targetRoom;
    private String preferredWeapon;
    private Weapon weaponUsed;

    public AttackWallAction(Room room) {
        super("Attack Wall", SensoryLevel.PHYSICAL_ACTIVITY);
        this.targetRoom = room;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "attacked a wall";
    }

    @Override
    public boolean hasRealSound() {
        return weaponUsed != null && weaponUsed.hasRealSound();
    }

    @Override
    public Sound getRealSound() {
        return weaponUsed.getRealSound();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Weapon) {
                opts.addOption(it.getFullName(whosAsking));
            }
        }
        opts.addOption(whosAsking.getCharacter().getDefaultWeapon().getFullName(whosAsking));
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
        if (weapon == null && preferredWeapon.equals(performingClient.getCharacter().getDefaultWeapon().getFullName(performingClient))) {
            weapon = performingClient.getCharacter().getDefaultWeapon();
        }

        if (weapon == null) {
            performingClient.addTolastTurnInfo("What? The " + preferredWeapon + " wasn't there? " + failed(gameData, performingClient));
            return;
        }
        weaponUsed = weapon;
        performingClient.addTolastTurnInfo("You attacked the wall towards " + targetRoom.getName());
        for (Actor a : targetRoom.getActors()) {
            a.addTolastTurnInfo(weapon.getWallDamageText());
        }

        dealDamageToRooms(gameData, performingClient, targetRoom, weapon);
    }

    protected void dealDamageToRooms(GameData gameData, Actor performingClient, Room targetRoom, Weapon weapon) {
        boolean broken = targetRoom.damageWallFromRoom(gameData, performingClient.getPosition(), weapon.doWallDamage(gameData, performingClient));
        if (broken) {
            performingClient.addTolastTurnInfo("You broke through the wall!");
        } else {
            performingClient.getPosition().damageWallFromRoom(gameData, targetRoom, weapon.doWallDamage(gameData, performingClient));
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        preferredWeapon = args.get(0);
    }
}
