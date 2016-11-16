package model.objects.general;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.foods.Banana;
import model.items.foods.Doughnut;
import model.items.general.GameItem;
import model.items.general.PackOfSmokes;
import model.items.general.ZippoLighter;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/05/16.
 */
public class VendingMachine extends ElectricalMachinery {

    private ArrayList<GameItem> selection;
    private GameItem selectedItem;

    public VendingMachine(Room r) {
        super("Vending Machine", r);
        selection = new ArrayList<>();
        selection.add(new Doughnut(null));
        selection.add(new Banana(null));
        selection.add(new PackOfSmokes());
        selection.add(new ZippoLighter());

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("vendingmachine", "vending.png", 3);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new Action("Use Vending Machine", SensoryLevel.OPERATE_DEVICE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "Fiddled with Vending Machine";
            }

            @Override
            protected void execute(GameData gameData, Actor performingClient) {
                performingClient.addItem(selectedItem.clone(), null);
                performingClient.addTolastTurnInfo("You got a " + selectedItem.getBaseName() + " from the Vending Machine.");
            }

            @Override
            public ActionOption getOptions(GameData gameData, Actor whosAsking) {
                ActionOption aop = super.getOptions(gameData, whosAsking);
                for (GameItem it : selection) {
                    aop.addOption(it.getBaseName() + " ($$ " + it.getCost() + ")");
                }
                return aop;
            }

            @Override
            public void setArguments(List<String> args, Actor performingClient) {
                for (GameItem it : selection) {
                    if (args.get(0).contains(it.getBaseName())) {
                        selectedItem = it;
                        break;
                    }
                }
            }
        });
    }
}
