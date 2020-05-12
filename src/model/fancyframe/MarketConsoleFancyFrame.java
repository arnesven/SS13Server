package model.fancyframe;

import model.Bank;
import model.GameData;
import model.Player;
import model.objects.consoles.Console;
import model.objects.consoles.MarketConsole;
import model.objects.general.CrateObject;
import model.objects.general.GameObject;
import util.HTMLText;

public class MarketConsoleFancyFrame extends ConsoleFancyFrame {
    private final MarketConsole console;

    public MarketConsoleFancyFrame(Player performingClient, GameData gameData, MarketConsole console) {
        super(performingClient.getFancyFrame(), console, gameData, "#00198a", "#a3f4ff");
        this.console = console;


        buildContent(performingClient, gameData);
    }

    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();
        content.append("_______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE HISTORY", "[history]"));
        content.append("<br><b>Station Funds:</b> $$ " + Bank.getInstance(gameData).getStationMoney() + "<br/>");

        for (GameObject obj : console.getPosition().getObjects()) {
            if (obj instanceof CrateObject) {
                String text = obj.getPublicName(performingClient) + " for $$ " + console.getValueFor((CrateObject)obj, gameData);
                content.append(HTMLText.makeImage(obj.getSprite(performingClient)) + " " +
                        HTMLText.makeFancyFrameLink("SELL " + text, "[sell]") + " " +
                        text + "<br/>");
            }
        }

        setData(console.getPublicName(performingClient), false, content.toString());
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        buildContent(player, gameData);
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {

    }
}
