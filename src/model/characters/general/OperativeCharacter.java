package model.characters.general;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.items.suits.Equipment;
import model.items.suits.SpaceSuit;
import model.actions.characteractions.EscapeAndSetNukeAction;
import model.actions.characteractions.StealAction;
import model.items.weapons.Revolver;
import model.map.rooms.NukieShipRoom;
import model.map.rooms.Room;
import model.objects.general.AirlockPanel;
import model.objects.general.GameObject;

public class OperativeCharacter extends HumanCharacter {

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
		if (hasAirlockPanel(getPosition()) && hasASpaceSuitOn() && GameItem.hasAnItemOfClass(getActor(), NuclearDisc.class)) {
			at.add(new EscapeAndSetNukeAction());
		}
		Action stealAction = new StealAction(this.getActor());
		if (stealAction.getOptions(gameData, this.getActor()).numberOfSuboptions() > 0) {
			at.add(stealAction);
		}
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
}
