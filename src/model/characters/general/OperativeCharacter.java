package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.decorators.LarcenistCharacter;
import model.characters.decorators.SpaceProtection;
import model.characters.special.MartialArtist;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.items.suits.Equipment;
import model.items.suits.SpaceSuit;
import model.actions.characteractions.EscapeAndSetNukeAction;
import model.actions.characteractions.StealAction;
import model.items.weapons.Revolver;
import model.map.GameMap;
import model.map.rooms.AirLockRoom;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import model.objects.general.AirlockPanel;
import model.objects.general.GameObject;
import model.objects.general.NuclearBomb;

public class OperativeCharacter extends HumanCharacter implements MartialArtist {

	private int num;

	public OperativeCharacter(int num, int startRoom) {
		super("Operative #" + num, startRoom, 17.0);
		this.num = num;
	}


    @Override
	public List<GameItem> getStartingItems() {
		List<GameItem> gi = new ArrayList<>();
		gi.add(new Revolver());
		return gi;
	}
	
	@Override
	public boolean isCrew() {
		return false;
	}
	
	@Override
	public void addCharacterSpecificActions(GameData gameData,
			ArrayList<Action> at) {
		super.addCharacterSpecificActions(gameData, at);
		LarcenistCharacter.addLarcenyActions(gameData, at, getActor());
	}

    private static boolean hasAirlockPanel(Room position) {
        for (GameObject ob : position.getObjects()) {
            if (ob instanceof AirlockPanel) {
                return true;
            }
        }
        return false;
    }


    private boolean hasASpaceSuitOn() {
		return getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) instanceof SpaceSuit;
	}

	@Override
	public GameCharacter clone() {
		return new OperativeCharacter(num, this.getStartingRoom());
	}


    @Override
    public List<Room> getVisibleMap(GameData gameData) {
        List<Room> rooms = super.getVisibleMap(gameData);
        for (Room r : gameData.getAllRooms()) {
        	if (r instanceof NukieShipRoom) {
				rooms.add(r);
			}
		}
        return rooms;
    }


	public static String getAntagonistDescription() {
		return "<font size=\"3\"><i>You are part of a team of operatives sent by the syndicate to " +
				"obtain the nuclear activation codes protected by the station's commanding officer. " +
				"If you are able to obtain them you can set off your high-jacked nuke and blow SS13 to hell.</i><br/>" +
				"<b>Abilities:</b> Pickpocketing <br/>" +
				"<b>Initiative:</b> 17.0</font>";
	}

	@Override
	public String getMugshotName() {
		return "Operative";
	}

	@Override
	public String getRadioName() {
		return "Somebody";
	}

	@Override
	public List<Room> getExtraMoveToLocations(GameData gameData) {
		try {
			if (gameData.getMap().getLevelForRoom(getPosition()).getName().equals(GameMap.STATION_LEVEL_NAME)) {
				if (getPosition() instanceof AirLockRoom) {
					List<Room> rooms = new ArrayList<>();
					for (Room r : gameData.getAllRooms()) {
						if (r instanceof NukieShipRoom) {
							rooms.add(r);
						}
					}
					return rooms;
				}
			}
		} catch (NoSuchThingException e) {
			e.printStackTrace();
		}
		return super.getExtraMoveToLocations(gameData);
	}

	@Override
	public void doAfterMovement(GameData gameData) {
		super.doAfterMovement(gameData);
		if (!isDead() && getPosition() instanceof NukieShipRoom && !getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof SpaceProtection)) {
			try {
				Room r = gameData.getRoom("Deep Space");
				getActor().moveIntoRoom(r);
				getActor().addTolastTurnInfo("You jumped out into space without a space suit!");
			} catch (NoSuchThingException e) {
				e.printStackTrace();
			}
		} else if (getPosition() instanceof NukieShipRoom && isBombRoom(getPosition())) {
			if (NukieShipRoom.hasTheDisk(getActor()) != null) {
				((NukieShipRoom)(getPosition())).activateNuke(gameData, (Player)getActor());
			}
		}

	}

	private boolean isBombRoom(Room position) {
		for (GameObject obj : position.getObjects()) {
			if (obj instanceof NuclearBomb) {
				return true;
			}
		}
		return false;
	}
}
