package model.actions.objectactions;

import java.util.List;

import model.characters.general.ChimpCharacter;
import model.events.damage.FireDamage;
import model.fancyframe.CookOMaticFancyFrame;
import model.items.EmptyContainer;
import model.items.NoSuchThingException;
import model.items.foods.GrilledMonkeyDeluxe;
import model.items.foods.RawFoodContainer;
import model.items.general.GameItem;
import model.items.suits.Equipment;
import model.map.rooms.Room;
import model.objects.general.CrateObject;
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

    private final CookOMaticFancyFrame fancyFrame;
    private CookOMatic cooker;
    private FoodItem selectedItem;
    private String chosenDestination;

    public CookFoodAction(CookOMatic cookOMatic, CookOMaticFancyFrame cmfa) {
        super("Cook Food", SensoryLevel.OPERATE_DEVICE);
        this.cooker = cookOMatic;
        this.fancyFrame = cmfa;
    }

    public CookFoodAction(CookOMatic cookOMatic) {
        this(cookOMatic, null);
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
        cookAndMaybeSendWithDumbwaiter(gameData, performingClient, selectedItem);
    }


    public void cookAndMaybeSendWithDumbwaiter(GameData gameData, Actor performingClient, FoodItem selectedItem) {

        double factor = 1.0;

        if (hasAChefsHatOn(performingClient)) {
            factor = 0.25;
            performingClient.addTolastTurnInfo("You are a master in the kitchen!");
        }

        if (removeRawFoodIfAble(gameData, performingClient)) {
            if (selectedItem.canBeCooked(gameData, performingClient)) {
                if (MyRandom.nextDouble() > selectedItem.getFireRisk() * factor) {
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
            } else {
                performingClient.addTolastTurnInfo("What, no raw material to use? " + failed(gameData, performingClient));
            }
        } else {
            performingClient.addTolastTurnInfo("No raw food to use! " + failed(gameData, performingClient));
        }

        fancyFrame.cookingIsDone(gameData, performingClient);
    }

    private boolean removeRawFoodIfAble(GameData gameData, Actor performingClient) {
        GameItem itToRemove = null;
        // First look for raw food in room itself
        for (GameItem it : performingClient.getPosition().getItems()) {
            if (it instanceof RawFoodContainer) {
                itToRemove = it;
                break;
            }
        }
        if (itToRemove != null) {
            performingClient.getPosition().getItems().remove(itToRemove);
            performingClient.getPosition().addItem(new EmptyContainer());
            return true;
        }


        // next look for raw food in crates in the room
        for (GameObject ob : performingClient.getPosition().getObjects()) {
            if (ob instanceof CrateObject) {
                for (GameItem it : ((CrateObject) ob).getInventory()) {
                    if (it instanceof RawFoodContainer) {
                        itToRemove = it;
                        break;
                    }
                }
                if (itToRemove != null) {
                    ((CrateObject) ob).getInventory().remove(itToRemove);
                    performingClient.getPosition().addItem(new EmptyContainer());
                    return true;
                }
            }
        }

        // finally look for raw food in player's inventory
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof RawFoodContainer) {
                itToRemove = it;
                break;
            }
        }
        if (itToRemove != null) {
            performingClient.getItems().remove(itToRemove);
            performingClient.getPosition().addItem(new EmptyContainer());
            return true;
        }
        Logger.log("Could not find raw food object to use up!");
        return false;
    }


    private boolean hasAChefsHatOn(Actor performingClient) {
        return performingClient.getCharacter().getEquipment().getEquipmentForSlot(Equipment.HEAD_SLOT) instanceof ChefsHat;
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
