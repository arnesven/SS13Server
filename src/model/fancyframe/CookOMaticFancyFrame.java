package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.CookFoodAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.items.foods.FoodItem;
import model.objects.general.CookOMatic;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class CookOMaticFancyFrame extends FancyFrame {
    private final CookOMatic cooker;
    private String whatsCooking = "";

    public CookOMaticFancyFrame(Player performingClient, GameData gameData, CookOMatic cooker) {
        super(performingClient.getFancyFrame());
        this.cooker = cooker;


        buildContent(performingClient, gameData);
    }

    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();

        content.append("<table border=\"1\">");
        content.append("<tr><td></td><td>Dish Name</td><td>" + HTMLText.makeText("black", "serif", 2, "Difficulty") + "</td></tr>");
        for (FoodItem food : CookOMatic.getCookableFood(performingClient)) {
            content.append("<tr><td>" + HTMLText.makeImage(food.getSprite(performingClient)) + "</td><");
            content.append("<td>" + food.getPublicName(performingClient) + " ");
            if (whatsCooking.equals(food.getBaseName())) {
                content.append("<i>Cooking...</i>");
            } else if (whatsCooking.equals("")) {
                content.append(HTMLText.makeFancyFrameLink("COOK " + food.getBaseName(), "[cook]"));
            }
            content.append("</td>");
            content.append("<td align=\"right\">" + (int)(food.getFireRisk()*100) + "</td>");
            content.append("</tr>");
        }
        content.append("</table>");


        setData(cooker.getPublicName(performingClient), false, HTMLText.makeColoredBackground("#e7ecb6", content.toString()));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("COOK")) {
            List<String> args = new ArrayList<>();
            this.whatsCooking = event.replace("COOK " , "");
            args.add(whatsCooking);
            CookFoodAction cfa = new CookFoodAction(cooker, this);
            cfa.setActionTreeArguments(args, player);
            player.setNextAction(cfa);
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        }
    }

    public void cookingIsDone(GameData gameData, Actor performingClient) {
        whatsCooking = "";
        buildContent((Player)performingClient, gameData);
    }
}
