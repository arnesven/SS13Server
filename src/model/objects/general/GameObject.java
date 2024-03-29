package model.objects.general;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;

public class GameObject implements SpriteObject, Serializable {

    private static int uidCounter = 1;
    private final int uid;
    private String name;
	private Room position;
    private Sprite sprite = new Sprite("gameobject", "computer.png", 0, this);
    private boolean hasAbsolutePosition;
    private double absX;
    private double absY;
    private double absZ;
    private RelativePositions relativePosition;

    public GameObject(String name, Room position, RelativePositions relPos) {
        this.uid = uidCounter++;
		this.name = name;
		this.position = position;
		hasAbsolutePosition = false;
		this.relativePosition = relPos;
	}

	public GameObject(String name, Room pos) {
        this(name, pos, null);
    }

	public String getBaseName() {
		return name;
	}

	public void setName(String name) {
        this.name = name;
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
        if (hasAbsolutePosition && forWhom.hasAbsolutePosition() && SpriteObject.distance(this, forWhom) >= 1.0) {
            return new ArrayList<>();
        }
        ArrayList<Action> acts = new ArrayList<>();
        if (forWhom.getCharacter().getsObjectActions() && forWhom.getCharacter().getsActions()) {
            addSpecificActionsFor(gameData, forWhom, acts);
        }
        return acts;
    }

    public boolean shouldBeSeenWhenNotInRoomBy(Player player) {
        return false;
    }

    @Override
    public void setAbsolutePosition(double x, double y, double z) {
        this.hasAbsolutePosition = true;
        this.absX = x;
        this.absY = y;
        this.absZ = z;
    }

    public void setAbsolutePosition(double x, double y) {
        setAbsolutePosition(x, y, 0.0);
    }



    @Override
    public double getAbsoluteX() {
        return absX;
    }

    @Override
    public double getAbsoluteY() {
        return absY;
    }

    @Override
    public double getAbsoluteZ() {
        return absZ;
    }

    @Override
    public boolean hasAbsolutePosition() {
        return hasAbsolutePosition;
    }

    public int getUid() {
        return uid;
    }

    @Override
    public void setPreferredRelativePosition(RelativePositions relpos) {
        this.relativePosition = relpos;
    }

    @Override
    public RelativePositions getPreferredRelativePosition() {
        return relativePosition;
    }
}
