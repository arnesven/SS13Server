package model.actions.roomactions;

import model.Actor;
import model.GameData;
import model.actions.AttackWallAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import sounds.Sound;

import java.awt.geom.Point2D;
import java.util.List;

public class AttackWallFromSpaceAction extends AttackWallAction {

    private final Point2D point;

    public AttackWallFromSpaceAction(Room room, Point2D p) {
        super(room);
        setName(String.format("Attack Wall at (x=%1.1f y=%1.1f)", p.getX(), p.getY()));
        this.point = p;
    }

    @Override
    protected void dealDamageToRooms(GameData gameData, Actor performingClient, Room targetRoom, Weapon weapon) {
        Point2D position = point;
        boolean broken = targetRoom.damageWallFromSpace(gameData, position,
                weapon.doWallDamage(gameData, performingClient), performingClient.getPosition());
        if (broken) {
            performingClient.addTolastTurnInfo("You broke through the wall!");
        } else {
            performingClient.getPosition().damageWallFromSpace(gameData, position,
                    weapon.doWallDamage(gameData, performingClient), performingClient.getPosition());
        }
    }
}
