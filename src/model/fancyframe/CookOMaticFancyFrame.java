package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.CookFoodAction;
import model.actions.objectactions.WalkUpToElectricalMachineryAction;
import model.items.NoSuchThingException;
import model.items.foods.FoodItem;
import model.map.rooms.Room;
import model.objects.general.CookOMatic;
import model.objects.general.Dumbwaiter;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class CookOMaticFancyFrame extends FancyFrame {
    private final CookOMatic cooker;
    private boolean showDumb;
    private String whatsCooking = "";
    private String sendToRoom = "";

    public CookOMaticFancyFrame(Player performingClient, GameData gameData, CookOMatic cooker) {
        super(performingClient.getFancyFrame());
        this.cooker = cooker;
        this.showDumb = false;


        buildContent(performingClient, gameData);
    }

    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();

        if (showDumb) {
            showDumbwaitorPage(performingClient, gameData, content);
        } else {
            showCookingPage(performingClient, gameData, content);
        }


        setData(cooker.getPublicName(performingClient), false, HTMLText.makeColoredBackground("#e7ecb6", content.toString()));
    }

    private void showDumbwaitorPage(Player performingClient, GameData gameData, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("CHANGEPAGE COM", "[cook-o-matic]") + "<br/>");
        content.append(HTMLText.makeCentered("<i>The station has a sophisticated food-delivery system! " +
                "The dumbwaiter can transport freshly made dishes (and other things) to many places.</i>"));

        try {
            Dumbwaiter dw = gameData.findObjectOfType(Dumbwaiter.class);
            for (Room r : dw.getDestinations(gameData)) {
                String symbol = "☐";
                if (sendToRoom.equals(r.getName())) {
                    symbol = "☑";
                }
                content.append(HTMLText.makeFancyFrameLink("SEND " + r.getName(),  HTMLText.makeText("black", "serif", 5, symbol)) +
                        " " + r.getName() + "<br/>");
            }
        } catch (NoSuchThingException e) {
            content.append(HTMLText.makeCentered("<b>No Dumbwaiter found!</b>"));
            e.printStackTrace();
        }
    }

    private void showCookingPage(Player performingClient, GameData gameData, StringBuilder content) {
        content.append(HTMLText.makeFancyFrameLink("CHANGEPAGE DUMB", "[dumbwaiter]") + "<br/>");
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
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("COOK")) {
            List<String> args = new ArrayList<>();
            this.whatsCooking = event.replace("COOK " , "");
            args.add(whatsCooking);
            CookFoodAction cfa = new CookFoodAction(cooker, this);
            if (!sendToRoom.equals("")) {
                args.add(sendToRoom);
            }
            cfa.setActionTreeArguments(args, player);
            player.setNextAction(cfa);
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        } else if (event.contains("CHANGEPAGE")) {
            showDumb = !showDumb;
            buildContent(player, gameData);
        } else if (event.contains("SEND")) {
            this.sendToRoom = event.replace("SEND ", "");
            buildContent(player, gameData);
        }
    }

    public void cookingIsDone(GameData gameData, Actor performingClient) {
        whatsCooking = "";
        buildContent((Player)performingClient, gameData);
    }
}
