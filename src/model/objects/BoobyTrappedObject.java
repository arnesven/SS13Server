package model.objects;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.TriggerAction;
import model.items.BombItem;
import model.map.Room;
import model.actions.SensoryLevel;

public class BoobyTrappedObject extends GameObject {

	private Actor rigger;
	private GameObject innerObject;
	private BombItem bomb;
	private Room room;

	public BoobyTrappedObject(GameObject selectedObject,
			BombItem bomb, Actor performingClient, Room room) {
		super(selectedObject.getName(), room);
		this.rigger = performingClient;
		this.innerObject = selectedObject;
		this.bomb = bomb;
		this.room = room;
	}
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Player cl,
			ArrayList<Action> at) {
		
		ArrayList<Action> innerActions = new ArrayList<>();
		System.out.println("Gettng inner actions " + innerActions.size());
		innerObject.addSpecificActionsFor(gameData, cl, innerActions);
		
		for (final Action a : innerActions) {
			Action triggerAction = new TriggerAction(a) {
				
				public void doTheAction(GameData gameData, Actor performingClient) {
					execute(gameData, performingClient);
					//this.performer = performingClient;
					//performingClient.getPosition().addToActionsHappened(this);
				}
				
				@Override
				protected void execute(GameData gameData, Actor performingClient) {
					performingClient.addTolastTurnInfo(BombItem.FOUND_A_BOMB_STRING);
					room.getObjects().remove(BoobyTrappedObject.this);
					room.addObject(innerObject);
					room.addItem(bomb);
					bomb.explode(gameData, rigger);
				}
			};
			at.add(triggerAction);
		}
	}

}
