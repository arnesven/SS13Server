package model.actions.ai;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.ElectrifyObjectAction;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by erini02 on 22/12/16.
 */
public class AIOverchargeAction extends Action {
    private final GameData gameData;
    private ElectricalMachinery selected;

    public AIOverchargeAction(GameData gameData) {
        super("Overcharge", SensoryLevel.NO_SENSE);
        this.gameData = gameData;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        List<ElectricalMachinery> objs = new ArrayList<>();


        for (Room r :  gameData.getMap().getRoomsForLevel("ss13")) {
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof ElectricalMachinery && ((ElectricalMachinery)ob).canBeOvercharged()) {
                    objs.add((ElectricalMachinery)ob);
                }
            }
        }

        Collections.sort(objs, new Comparator<ElectricalMachinery>() {
            @Override
            public int compare(ElectricalMachinery electricalMachinery, ElectricalMachinery t1) {
                return electricalMachinery.getName().compareTo(t1.getName());
            }
        });
        for (ElectricalMachinery em : objs) {
            opt.addOption(em.getPublicName(whosAsking) + " (" + em.getPosition().getName() + ")");

        }

        return opt;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "overchared";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selected == null) {
            performingClient.addTolastTurnInfo("What, object not found? " + failed(gameData, performingClient));
            return;
        }

        ElectrifyObjectAction ele = new ElectrifyObjectAction(selected);
        ele.setArguments(new ArrayList<>(), performingClient);
        ele.doTheAction(gameData, performingClient);
        performingClient.addTolastTurnInfo("The " + selected.getName() + " was overcharged. It'll shock whoever touches it!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Room r :  gameData.getMap().getRoomsForLevel("ss13")) {
            for (GameObject ob : r.getObjects()) {
                if (ob instanceof ElectricalMachinery) {
                    if (args.get(0).contains(ob.getPublicName(performingClient) + " (" + ob.getPosition().getName() + ")")) {
                        selected = (ElectricalMachinery)ob;
                    }
                }
            }
        }
    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("aioverchargeabi", "interface_robot.png", 7, 2, null);
    }
}
