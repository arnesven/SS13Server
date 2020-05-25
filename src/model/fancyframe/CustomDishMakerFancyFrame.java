package model.fancyframe;

import clientview.components.InteractableHtmlPane;
import graphics.sprites.PhysicalBody;
import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.objectactions.CookFoodAction;
import model.map.doors.PowerCord;
import model.objects.general.CookOMatic;
import util.HTMLText;
import util.Logger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CustomDishMakerFancyFrame extends FancyFrame {
    private final CookOMatic cooker;
    private boolean showBaseSelection;
    private String selectedBase;
    private boolean showSummary;
    private String dishName;
    private boolean showRename;
    private int selectedCondiment;
    private boolean showCondiment;
    private Color condColor;

    public CustomDishMakerFancyFrame(Player player, GameData gameData, CookOMatic cooker) {
        super(player.getFancyFrame());
        this.cooker = cooker;
        showBaseSelection = true;
        showSummary = false;
        showRename = false;
        dishName = "Unnamed";
        selectedCondiment = 0;
        condColor = Color.BLACK;

        makeContent(player, gameData);
    }

    private void makeContent(Player player, GameData gameData) {
        StringBuilder content = new StringBuilder();
        if (showBaseSelection) {
            baseSelectionPage(player, gameData, content);
        } else if (showSummary) {
            summaryPage(player, gameData, content);
        } else if (showRename) {
            content.append("<b>" + "Give your dish a cool name!</b>");
        } else if (showCondiment) {
            condimentPage(player, gameData, content);
        }


        this.setData("Create Custom Dish", showRename, HTMLText.makeColoredBackground("#e7ecb6", content.toString()));
    }

    private void condimentPage(Player player, GameData gameData, StringBuilder content) {
        content.append("<b>Add a Condiment:<b></br>");
        content.append(HTMLText.makeCentered(HTMLText.makeImage(makeTotalSprite())));
        int col = 0;
        int count = 0;
        for (Sprite sp : cooker.getCustomDishDesigner().getCondiments(condColor)) {
            content.append(HTMLText.makeFancyFrameLink("SETCOND " + count, HTMLText.makeImage(sp)));
            col++;
            count++;
            if (col == 7) {
                col = 0;
                content.append("<br/>");
            }
        }

        content.append("<br/>");
        col = 0;
        for (Color color : getAllCondColors()) {
            content.append(HTMLText.makeFancyFrameLink("CONDCOLOR " + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue(),
                    HTMLText.makeText(toHex(color), "serif",6, "■")));
            col++;
            if (col == 12) {
                col = 0;
                content.append("<br/>");
            }
        }
        content.append("<br/>" + HTMLText.makeCentered(HTMLText.makeFancyFrameLink("BACKCOND", "DONE")));
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void summaryPage(Player player, GameData gameData, StringBuilder content) {
        content.append("<b>Name</b>: " + dishName + "<br/>");
        content.append("<b>Appearance:</b>" + HTMLText.makeImage(makeTotalSprite()) + "<br/>");
        content.append("<b>Condiment:</b>");
        content.append(" no. " + selectedCondiment);
        content.append(HTMLText.makeFancyFrameLink("CONDIMENT", "[change]") + "<br/>");
        content.append("<b>Decoration:</b>" + "<br/>");
        content.append("<br/>");
        if (!dishName.equals("Unnamed")) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("DONE", "SAVE AND COOK!")));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("RENAME", "NAME YOUR DISH")));
        }
    }

    private void baseSelectionPage(Player player, GameData gameData, StringBuilder content) {
        content.append("<b>Select a base for your new dish:</b><br/>");
        int col = 0;
        for (Sprite sp : cooker.getCustomDishDesigner().getBaseSprites()) {
            content.append(HTMLText.makeFancyFrameLink("BASE " + sp.getName(), HTMLText.makeImage(sp)));
            col++;
            if (col == 7) {
                content.append("<br/>");
                col = 0;
            }
        }
    }

    private Sprite makeTotalSprite() {
        return cooker.getCustomDishDesigner().getTotalSprite(selectedBase, selectedCondiment, condColor);
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("BASE")) {
            this.selectedBase = event.replace("BASE ", "");
            Logger.log("Selected base is " + selectedBase);
            this.showSummary = true;
            this.showBaseSelection = false;
            makeContent(player, gameData);
        } else if (event.contains("RENAME")) {
            this.showRename = true;
            this.showSummary = false;
            makeContent(player, gameData);
        } else if (event.contains("CONDIMENT")) {
            this.showSummary = false;
            this.showCondiment = true;
            makeContent(player, gameData);
        } else if (event.contains("SETCOND")) {
            selectedCondiment = Integer.parseInt(event.replace("SETCOND ", ""));
            //showCondiment = false;
            //showSummary = true;
            makeContent(player, gameData);
        } else if (event.contains("CONDCOLOR")) {
            String[] parts = event.replace("CONDCOLOR ", "").split("-");
            this.condColor = new Color(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            makeContent(player, gameData);
        } else if (event.contains("BACKCOND")) {
            this.showSummary = true;
            this.showCondiment = false;
            makeContent(player, gameData);
        } else if (event.contains("DONE")) {
            cooker.getCustomDishDesigner().saveDish(selectedBase, selectedCondiment, condColor, dishName, player);
            CookFoodAction cfa = new CookFoodAction(cooker);
            List<String> args = new ArrayList<>();
            args.add(dishName);
            cfa.setActionTreeArguments(args, player);
            player.setNextAction(cfa);
            dispose(player);
        }
        readyThePlayer(gameData, player);
    }

    @Override
    public void handleInput(GameData gameData, Player player, String data) {
        super.handleInput(gameData, player, data);
        String forbidden = ":/\\(){}&%$¤#@_|<>^'*~;.,£+[]\"!?";
        for (int i = 0; i < forbidden.length(); ++i) {
            data.replace(forbidden.charAt(i)+"", "");
        }
        this.dishName = data;
        showSummary = true;
        showRename = false;
        readyThePlayer(gameData, player);
        makeContent(player, gameData);
    }


    private static List<Color> getAllCondColors() {
        List<Color> result = new ArrayList<>();
        for (int r = 0; r < 255; r += 63) {
            for (int g = 0; g < 255; g += 63) {
                for (int b = 0; b < 255; b += 63) {
                    result.add(new Color(r, g, b));
                }
            }
        }
        return result;
    }

}
