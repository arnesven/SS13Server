package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.MolotovCocktail;
import model.items.NoSuchThingException;
import model.items.foods.Alcohol;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class MakeMolotovAndThrowAction extends Action {
    private String location;
    private GameItem selectedBurnable;

    public MakeMolotovAndThrowAction(Actor cl) {
        super("Throw Molotov", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "threw a molotov cocktail";
    }

    private List<GameItem> getBurnables(Actor whosAsking) {
        List<GameItem> list = new ArrayList<>();
         for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Alcohol) {
                if (((Alcohol)it).getPotency() > 2) {
                    list.add(it);
                }
            } else if (it instanceof Chemicals) {
                list.add(it);
            }
        }
        return list;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem it : getBurnables(whosAsking)) {
            ActionOption opt = new ActionOption(it.getPublicName(whosAsking));
            for (Room r : whosAsking.getPosition().getNeighborList()) {
                opt.addOption(r.getName());
            }
            opt.addOption("This room");
            opts.addOption(opt);
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedBurnable == null) {
            performingClient.addTolastTurnInfo("What, nothing to burn! " + Action.FAILED_STRING);
            return;
        }
        performingClient.getItems().remove(selectedBurnable);
		performingClient.addTolastTurnInfo("You threw the molotov cocktail into " + location + ".");
        Room targetRoom = null;
        try {
            targetRoom = gameData.getRoom(location);

            MolotovCocktail mol = new MolotovCocktail(selectedBurnable);
            targetRoom.addItem(mol);
		    Action a = new ExplosionAction(mol, targetRoom);
		    a.doTheAction(gameData, performingClient);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selectedBurnable = null;
        for (GameItem it : getBurnables(performingClient)) {
            if (it.getPublicName(performingClient).equals(args.get(0))) {
                selectedBurnable = it;
            }
        }

        if (args.get(1).equals("This room")) {
			location = performingClient.getPosition().getName();
		} else {
			location = args.get(1);
		}
    }
}
