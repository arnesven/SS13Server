package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.ElectronicParts;
import model.items.general.GameItem;
import model.objects.AITurret;
import model.objects.ATM;
import model.objects.StasisPod;
import model.objects.consoles.*;
import model.objects.general.*;

import java.util.*;

/**
 * Created by erini02 on 17/11/16.
 */
public class BuildElectronicObjectAction extends Action {

    private final Collection<GameObject> allBuildableObjects;
    private GameObject selected;

    public BuildElectronicObjectAction(GameData gameData) {
        super("Construct", SensoryLevel.PHYSICAL_ACTIVITY);

        allBuildableObjects = getBuildableObjects(gameData);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "constructed a new " + selected.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        List<GameObject> list = new ArrayList<>();
        list.addAll(allBuildableObjects);
        Collections.sort(list, ((GameObject o1, GameObject o2) -> o1.getPublicName(whosAsking).compareTo(o2.getPublicName(whosAsking))));
        for (GameObject obj : list) {
            opts.addOption(obj.getPublicName(whosAsking));
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You built a new " + selected.getPublicName(performingClient) + ".");
        selected.setPosition(performingClient.getPosition());
        performingClient.getPosition().addObject(selected);
        try {
            GameItem it = GameItem.getItemFromActor(performingClient, new ElectronicParts());
            performingClient.getItems().remove(it);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameObject obj : allBuildableObjects) {
            if (obj.getPublicName(performingClient).equals(args.get(0))) {
                selected = obj;
                break;
            }
        }
    }

    public Collection<GameObject> getBuildableObjects(GameData gameData) {
        Set<GameObject> set = new HashSet<>();
        set.add(new AirLockControl(null));
        set.add(new GeneticsConsole(null));
        set.add(new SecurityCameraConsole(null));
        set.add(new BioScanner(null));
        set.add(new CookOMatic(null));
        set.add(new Dumbwaiter(null));
        set.add(new AdministrationConsole(null, gameData));
        set.add(new NuclearBomb(null));
        set.add(new AirlockPanel(null));
        set.add(new SlotMachine(null));
        set.add(new VendingMachine(null));
        try {
            set.add(new AITurret(null, gameData.findObjectOfType(AIConsole.class), gameData));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        set.add(new ATM(gameData, null));
        set.add(new StasisPod(null));
        return set;
    }
}
