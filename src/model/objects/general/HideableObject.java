package model.objects.general;

import model.Actor;
import model.Player;
import model.map.rooms.Room;

import java.util.ArrayList;

/**
 * Created by erini02 on 16/09/17.
 */
public abstract class HideableObject extends BreakableObject {

    private boolean foundByHumanTeam = false;
    private Actor finder = null;

    public HideableObject(String name, double starthp, Room r) {
        super(name, starthp, r);
    }

    @Override
    public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
        if (isFound()){
            super.addYourselfToRoomInfo(info, whosAsking);
        }
    }


    public void setFound(boolean b) {
        foundByHumanTeam = b;
    }

    public boolean isFound() {
        return foundByHumanTeam;
    }

    public void setFinder(Actor finder) {
        this.finder = finder;
    }

    public Actor getFinder() {
        return finder;
    }

    @Override
    public boolean isTargetable() {
        return isFound();
    }


}
