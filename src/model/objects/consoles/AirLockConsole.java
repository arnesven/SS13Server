package model.objects.consoles;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ApproveVentStation;
import model.actions.objectactions.SitDownAtAirLockControl;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.general.UniversalKeyCard;
import model.map.rooms.Room;
import model.actions.objectactions.AirlockOverrideAction;

public class AirLockConsole extends Console {

    private int ventApprovedRound;

    public AirLockConsole(Room pos) {
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
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new AirlockOverrideAction(gameData));
//		if (mayApproveVenting(cl)) {
//            at.add(new ApproveVentStation(this));
//        }

        if (cl instanceof Player) {
            at.add(new SitDownAtAirLockControl(gameData, this));
        }
	}

    public boolean mayApproveVenting(Actor cl) {
        if (GameItem.hasAnItemOfClass(cl, UniversalKeyCard.class) ||
                cl.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof AICharacter)) {
            return true;
        }
        return false;
    }

}
