package model.fancyframe;

import model.GameData;
import model.Player;
import model.items.general.GameItem;
import util.HTMLText;

public class ItemDescriptionFancyFrame extends FancyFrame {
    private final GameItem item;

    public ItemDescriptionFancyFrame(Player performingClient, GameData gameData, GameItem item) {
        super(performingClient.getFancyFrame());
        this.item = item;

        buildContent(performingClient, gameData);
    }

    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();
        content.append(HTMLText.makeCentered("<b>" + item.getPublicName(performingClient) + "</b><br/>" +
                HTMLText.makeImage(item.getSprite(performingClient)) + "<br/>" +
                makeItemStats(performingClient) + "<br>" +
                item.getExtraDescriptionStats(gameData, performingClient)      ));
        //content.append("<br/>");
        content.append("<table border=\"1\"><tr><td>" + item.getDescription(gameData, performingClient) + "</td></tr></table>");

        setData("Item Info", false, HTMLText.makeColoredBackground("#ecede4", content.toString()));
    }

    private String makeItemStats(Player performingClient) {
        return "<b>Weight:</b> " + item.getWeight() + " kg, <b>Cost:</b>" + item.getCost();
    }
}
