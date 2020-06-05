package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.DefuseBombAction;
import model.actions.itemactions.DismantleAction;
import model.actions.itemactions.RepairAction;
import model.actions.itemactions.SealHullBreachAction;
import model.items.NoSuchThingException;
import model.items.weapons.BluntWeapon;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.Repairable;


public abstract class Tools extends BluntWeapon {

	public Tools(String typeName, int cost) {
		super(typeName + " Tools", 0.5, cost, 0.80);
	}


	@Override
	public abstract Sprite getHandHeldSprite();

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return getToolsDescription(gameData, performingClient) + ". Good for manually opening doors and defusing bombs." + //"Good for repairing equipment, sealing hull breaches, defusing bombs, hacking doors "
				super.getDescription(gameData, performingClient);
	}

	protected abstract String getToolsDescription(GameData gameData, Player performingClient);
}
