package model.actions.objectactions;

import java.util.List;

import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.general.Dumbwaiter;
import model.objects.general.GameObject;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.crew.ChefCharacter;
import model.characters.decorators.InstanceChecker;
import model.items.foods.FoodItem;
import model.items.suits.ChefsHat;
import model.objects.general.CookOMatic;

public class CookFoodAction extends Action {

	private CookOMatic cooker;
	private FoodItem selectedItem;
    private String chosenDestination;

    public CookFoodAction(CookOMatic cookOMatic) {
		super("Cook Food", SensoryLevel.OPERATE_DEVICE);
		this.cooker = cookOMatic;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "cooked food";
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption res = new ActionOption("Cook Food");
        Dumbwaiter dumb = null;

        for (GameObject ob : cooker.getPosition().getObjects()) {
            if (ob instanceof Dumbwaiter) {
                dumb = (Dumbwaiter) ob;
            }
        }

		for (FoodItem gi : CookOMatic.getCookableFood(whosAsking)) {
            ActionOption opt = new ActionOption(gi.getBaseName());

            opt.addOption("Into inventory");
            if (dumb != null) {
                for (Room r : dumb.getDestinations(gameData)) {
                    opt.addOption("Send to " + r.getName());
                }
            }
			res.addOption(opt);
		}
		return res;
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		double factor = 1.0;
		
		if (hasAChefsHatOn(performingClient)) {
			factor = 0.25;
			performingClient.addTolastTurnInfo("You are a master in the kitchen!");
		}
		
		if (MyRandom.nextDouble() > selectedItem.getFireRisk()*factor) {
            String result = "You successfully cooked a " +
                    selectedItem.getPublicName(performingClient);
            if (chosenDestination == null || chosenDestination.contains("inventory")) {
                performingClient.addItem(selectedItem, cooker);
                performingClient.addTolastTurnInfo(result + ".");
            } else {
                try {
                    List<Room> dumbwaiterRooms = gameData.findObjectOfType(Dumbwaiter.class).getDestinations(gameData);
                    for (Room r : dumbwaiterRooms) {
                        if (chosenDestination.contains(r.getName())) {
                            r.addItem(selectedItem);
                            performingClient.addTolastTurnInfo(result + ", it was sent to " + r.getName() + ".");
                        }
                    }
                } catch (NoSuchThingException e) {
                    Logger.log(Logger.CRITICAL, "WHAT? No dumbwaiter found? But cooker got to choose destinations!!!");
                }
            }

		} else {
			gameData.getGameMode().addFire(performingClient.getPosition());
			performingClient.addTolastTurnInfo("You accidentally started a fire while cooking!");
		}
		
	}
	
	
	private boolean hasAChefsHatOn(Actor performingClient) {
		return performingClient.getCharacter().getSuit() instanceof ChefsHat;
	}

	public FoodItem getSelectedItem() {
		return selectedItem;
	}

	private boolean isAChef(Actor performingClient) {
		InstanceChecker chefChecker = new InstanceChecker() {
			
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof ChefCharacter;
			}
		};
		
		return performingClient.getCharacter().checkInstance(chefChecker);
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		for (FoodItem it : CookOMatic.getCookableFood(p)) {
			if (it.getBaseName().equals(args.get(0))) {
				selectedItem = it;
			}
		}

        if (args.size() > 1) {
            chosenDestination = args.get(1);
        }
	}

}
