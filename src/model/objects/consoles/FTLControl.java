package model.objects.consoles;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.JumpStationAction;
import model.actions.objectactions.SitDownAtFTLControl;
import model.actions.objectactions.SpinUpFTLAction;
import model.map.rooms.Room;

import java.util.*;

/**
 * Created by erini02 on 07/12/16.
 */
public class FTLControl extends Console {

    private boolean spunUp = false;

    public FTLControl(Room bridge) {
        super("FTL Control", bridge);
        setPowerPriority(1);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (spunUp) {
            at.add(new JumpStationAction(this));
        } else {
            at.add(new SpinUpFTLAction(this));
        }
        if (cl instanceof Player) {
            at.add(new SitDownAtFTLControl(gameData, this, (Player)cl));
        }
    }

    @Override
    public double getPowerConsumption() {
        if (spunUp) {
            return 10.0 * super.getPowerConsumption();
        }
        return super.getPowerConsumption();
    }

    public boolean isSpunUp() {
        return spunUp;
    }

    public void setSpunUp(boolean b) {
        this.spunUp = b;
    }

}
