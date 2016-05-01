package model.objects.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.TriggerAction;
import model.items.general.BombItem;
import model.map.Room;

public class BoobyTrappedObject extends GameObject {

	private Actor rigger;
	private GameObject innerObject;
	private BombItem bomb;
	private Room room;

	public BoobyTrappedObject(GameObject selectedObject,
			BombItem bomb, Actor performingClient, Room room) {
		super(selectedObject.getPublicName(performingClient), room);
		this.rigger = performingClient;
		this.innerObject = selectedObject;
		this.bomb = bomb;
		this.room = room;
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return innerObject.getSprite(whosAsking);
    }

    @Override
    public <T extends GameObject> boolean isOfType(Class<T> className) {
        return innerObject.isOfType(className);
    }

    @Override
    public GameObject getTrueObject() {
        return innerObject;
    }

    @Override
	public void addSpecificActionsFor(GameData gameData, Player cl,
			ArrayList<Action> at) {
		
		ArrayList<Action> innerActions = new ArrayList<>();
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
