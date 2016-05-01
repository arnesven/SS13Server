package model.objects.general;

import java.io.Serializable;
import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.Room;

public class GameObject implements Serializable {
	private String name;
	private Room position;
    private Sprite sprite = new Sprite("gameobject", "computer.png", 0);

    public GameObject(String name, Room position) {
		this.name = name;
		this.position = position;
	}

	public String getBaseName() {
		return name;
	}
	
	public String getPublicName(Actor whosAsking) {
		return getBaseName();
	}
	
	public Room getPosition() {
		return position;
	}
	

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add(getSprite(whosAsking).getName() + "<img>" + this.getPublicName(whosAsking));
	}

    public Sprite getSprite(Player whosAsking) {
        return sprite;
    }


//    protected char getIcon(Player whosAsking) {
//		return 'o';
//	}

	public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
	}


    public <T extends GameObject> boolean isOfType(Class<T> className) {
        return className.isInstance(this);
    }

    public GameObject getTrueObject() {
        return this;
    }
}
