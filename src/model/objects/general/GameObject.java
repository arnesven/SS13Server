package model.objects.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.ClientInfo;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.map.rooms.Room;

public class GameObject implements SpriteObject, Serializable {
	private String name;
	private Room position;
    private Sprite sprite = new Sprite("gameobject", "computer.png", 0, this);
    private boolean hasAbsolutePosition;
    private double absX;
    private double absY;

    public GameObject(String name, Room position) {
		this.name = name;
		this.position = position;
		hasAbsolutePosition = false;
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

    public void setPosition(Room pos) {
        position = pos;
    }
	

	public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
		info.add(getSprite(whosAsking).getName() + "<img>" + this.getPublicName(whosAsking));
	}

    public Sprite getSprite(Player whosAsking) {
        return sprite;
    }

    @Override
    public final Sprite getSprite(Actor whosAsking) {
        return getSprite((Player)whosAsking);
    }


//    protected char getIcon(Player whosAsking) {
//		return 'o';
//	}

	public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
	}


    public <T extends GameObject> boolean isOfType(Class<T> className) {
        return className.isInstance(this);
    }

    public GameObject getTrueObject() {
        return this;
    }


    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        ArrayList<Action> acts = new ArrayList<>();
        addSpecificActionsFor(gameData, forWhom, acts);
        return acts;
    }

    public boolean shouldBeSeenWhenNotInRoomBy(Player player) {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y) {
        this.hasAbsolutePosition = true;
        this.absX = x;
        this.absY = y;
    }

    @Override
    public double getAbsoluteX(ClientInfo clientInfo) {
        return absX;
    }

    @Override
    public double getAbsoluteY(ClientInfo clientInfo) {
        return absY;
    }

    @Override
    public boolean hasAbsolutePosition() {
        return hasAbsolutePosition;
    }
}
