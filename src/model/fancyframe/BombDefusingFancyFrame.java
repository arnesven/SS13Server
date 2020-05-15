package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.itemactions.DefuseBombAction;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.items.general.Multimeter;
import model.map.doors.PowerCord;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class BombDefusingFancyFrame extends FancyFrame {
    private final BombItem bomb;

    public BombDefusingFancyFrame(Player performingClient, GameData gameData, BombItem bomb) {
        super(performingClient.getFancyFrame());
        this.bomb = bomb;
        setHeight(270);


        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player performingClient) {
        if (bomb.isDefused() || bomb.isExploded()) {
            this.dispose(performingClient);
        }
        StringBuilder content = new StringBuilder();
        if (bomb.getDefusal().isBeingDefused()) {
            content.append("<br/>" + HTMLText.makeCentered("Something is happening with the bomb..." + "<br/>"));
        } else {
            content.append("<table width=\"100%\"><tr>");
            content.append("<td>" + HTMLText.makeImage(new Sprite("bombnote", "items.png", 38, 0, null)) + "</td>");
            content.append("<td>" + HTMLText.makeText("black", "serif", 3,
                    "There's a note on this bomb from somebody named " + bomb.getDefuseTip()) + "</td>");
            content.append("</tr></table>");
            content.append("<center><table bgcolor=\"#BBBBBB\" style=\"border:1px solid black\">");
            int i = 0;
            for (PowerCord pc : bomb.getPowerCords()) {
                content.append("<tr><td>");
                if (GameItem.hasAnItemOfClass(performingClient, Multimeter.class)) {
                    content.append("<td>" + pc.getStateAsChar() + "</td>");
                }
                content.append("<td>" + pc.drawYourselfInHTML(performingClient) + "</td>");
                content.append("<td>" + HTMLText.makeFancyFrameLink("CUT " + i, HTMLText.makeText("blue", "[cut]")) + "</td>");
                i++;
            }
            content.append("</table>");
            content.append(HTMLText.makeFancyFrameLink("BUTTON RED", HTMLText.makeImage(new Sprite("redbutton", "floors.png", 10, 19, null))));
            content.append(HTMLText.makeFancyFrameLink("BUTTON GREEN", HTMLText.makeImage(new Sprite("greenbutton", "floors.png", 11, 19, null))));
            content.append("</center>");
        }
        setData("Defusing Bomb!", false, HTMLText.makeColoredBackground("#ec7d7d", content.toString()));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("CUT")) {
            int index = Integer.parseInt(event.replace("CUT ", ""));
            bomb.getPowerCords().get(index).cut(player, gameData);
            buildContent(gameData, player);
        } else if (event.contains("BUTTON")) {
            bomb.getDefusal().pressButton(event.replace("BUTTON ", ""));
            DefuseBombAction dba = new DefuseBombAction(bomb);
            player.setNextAction(dba);
            readyThePlayer(gameData, player);
            bomb.getDefusal().setBeingDefused(true);
            buildContent(gameData, player);
        }
    }
}
