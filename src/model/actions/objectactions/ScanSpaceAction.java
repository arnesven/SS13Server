package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;

import java.util.Collection;
import java.util.List;

/**
 * Created by erini02 on 15/09/17.
 */
public class ScanSpaceAction extends Action {
    public ScanSpaceAction(GameData gameData) {
        super("Scan Space", SensoryLevel.OPERATE_DEVICE);

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Teletronics";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        for (int x=0; x < 3; ++x) {
            for (int y=0; y < 3; ++y) {
                for (int z=0; z < 3; ++z) {
                    String levelname = gameData.getMap().getLevelForCoordinates(new Integer[]{x, y, z}, gameData);
                    String coordString = x+"**-"+y+"**-"+z+"**";
                    if (levelname.contains("emptylevel")) {

                    } else if (levelname.equals("derelict")) {
                        performingClient.addTolastTurnInfo(coordString + " unidentified object.");
                    } else if (levelname.contains("exotic planet")) {
                        performingClient.addTolastTurnInfo(coordString + " planet.");
                    } else if (levelname.contains("asteroid field")) {
                        performingClient.addTolastTurnInfo(coordString + " asteroid field.");
                    } else if (levelname.contains("deep space")) {
                        for (Room r : gameData.getMap().getRoomsForLevel(levelname)) {
                            if (r.getActors().size() > 0) {
                                performingClient.addTolastTurnInfo(coordString + " Someone tumbling through space!");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
