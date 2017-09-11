package model.objects.consoles;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.objectactions.ApproveVentStation;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.rooms.Room;
import model.actions.objectactions.AirlockOverrideAction;

public class AirLockControl extends Console {

    private int ventApprovedRound;

    public AirLockControl(Room pos) {
		super("Airlock Override", pos);
        ventApprovedRound = 0;
	}

    public void setVentApprovedRound(int ventApprovedRound) {
        this.ventApprovedRound = ventApprovedRound;
    }

    public int getVentApprovedRound() {
        return ventApprovedRound;
    }

    @Override
	protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new AirlockOverrideAction(gameData));
        if (GameItem.hasAnItemOfClass(cl, KeyCard.class) ||
                cl.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof AICharacter)) {
            at.add(new ApproveVentStation(this));
        }
	}
	

}
