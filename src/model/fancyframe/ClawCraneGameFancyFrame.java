package model.fancyframe;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.general.GameItem;
import model.map.doors.PowerCord;
import model.objects.clawcrane.ClawCraneGame;
import util.HTMLText;
import util.MyRandom;

import java.awt.*;

public class ClawCraneGameFancyFrame extends SinglePersonUseMachineFancyFrame {
    private final ClawCraneGame game;
    private boolean finalizing = false;
    private boolean coinPaid = false;
    private GameItem outItem = null;

    public ClawCraneGameFancyFrame(Player performingClient, GameData gameData, ClawCraneGame clawCraneGame) {
        super(performingClient, clawCraneGame);
        this.game = clawCraneGame;
        setHeight(getHeight()+20);
        buildContent(performingClient, gameData);
    }


    private void buildContent(Player performingClient, GameData gameData) {
        StringBuilder content = new StringBuilder();

        if (!game.isPowered()) {
            content.append(HTMLText.makeCentered(makeTop(false)));
            content.append(HTMLText.makeCentered(HTMLText.makeImage(game.makeImageFromContents(false))));
            content.append(HTMLText.makeCentered(makeControls(false)));
            content.append(HTMLText.makeCentered(makeBottomRom(false, performingClient)));
        } else {
            content.append(HTMLText.makeCentered(makeTop(coinPaid)));
            content.append(HTMLText.makeCentered(HTMLText.makeImage(game.makeImageFromContents(coinPaid))));
            content.append(HTMLText.makeCentered(makeControls(!finalizing && coinPaid)));
            content.append(HTMLText.makeCentered(makeBottomRom(!coinPaid, performingClient)));
        }


        setData(game.getPublicName(performingClient), false, HTMLText.makeColoredBackground("gray", content.toString()));
    }

    private String makeBottomRom(boolean b, Player player) {
        StringBuilder bldr = new StringBuilder();
        bldr.append("<table width=\"70%\" border=\"1\"><tr>");
        bldr.append("<td>" + makeCoinSlot(b) + "</td>");
        bldr.append("<td bgcolor=\"black\" style=\"text-align:center\">" + makeOutSlot(player) + "</td>");
        bldr.append("</tr></table>");
        return bldr.toString();
    }

    private String makeOutSlot(Player player) {
        if (outItem == null) {
            return  HTMLText.makeImage(Sprite.blankSprite());
        }
        return  HTMLText.makeImage(outItem.getSprite(player));
    }

    private String yellowSerif(String ind) {
        return HTMLText.makeText("yellow", "Serif", 4, "<b>" + ind + "</b>");
    }

    private String yellowFat(String ind) {
        return HTMLText.makeText("yellow", "Courier", 6, "<b>" + ind + "</b>");
    }

    private String makeTop(boolean b) {
        if (b) {
            return makeRandomLights() + yellowSerif( "! GOOD LUCK ! " + makeRandomLights());
        }
        return HTMLText.makeText("black", "Serif", 4,"☀ ☀ ☀ <b>! GOOD LUCK !</b> ☀ ☀ ☀ ");
    }

    private String makeRandomLights() {
        StringBuilder buf = new StringBuilder();
        for (int i = 3; i > 0; --i) {
            Color col = PowerCord.randomPowerCordColor();
            String colStr = String.format("#%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());
            buf.append(HTMLText.makeText(colStr, "Serif", 4, "☀ "));
        }
        return buf.toString();
    }

    private String makeCoinSlot(boolean b) {
        if (b) {
            return HTMLText.makeFancyFrameLink("COINPAID", yellowSerif("Insert $$ 5"));
        }
        return HTMLText.makeText("black", "Serif", 4,"<b>Insert $$ 5</b>");
    }


    private String makeControls(boolean isActive) {
        StringBuilder content = new StringBuilder();
        if (isActive) {
            content.append(HTMLText.makeFancyFrameLink("MLEFT 9", yellowFat("↞ ")) +
                    HTMLText.makeFancyFrameLink("MLEFT 4", yellowFat("↤ ")) +
                    HTMLText.makeFancyFrameLink("GRAB", yellowFat("↨ ")) +
                    HTMLText.makeFancyFrameLink("MRIGHT 4", yellowFat("↦ ")) +
                    HTMLText.makeFancyFrameLink("MRIGHT 9", yellowFat("↠")));
        } else {
            content.append(HTMLText.makeText("black", "Courier", 6,"<b>↞ ↤ ↨ ↦ ↠</b>"));
        }


        return content.toString();
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("MLEFT")) {
            game.moveLeft(Integer.parseInt(event.replace("MLEFT ", "")));
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        } else if (event.contains("MRIGHT")) {
            game.moveRight(Integer.parseInt(event.replace("MRIGHT ", "")));
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        } else if (event.contains("GRAB")) {
            Action a = game.grabOrDrop(gameData, player, this);
            if (a != null) {
                player.setNextAction(a);
                this.finalizing = true;
            }
            gameData.getChat().serverInSay("Something is happening with the game...", player);
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        } else if (event.contains("COINPAID")) {
            coinPaid = game.startGame(gameData, player);
            outItem = null;
            readyThePlayer(gameData, player);
            buildContent(player, gameData);
        }
    }

    @Override
    protected void beingDisposed() {
        super.beingDisposed();
        game.resetYourself();
    }

    public void gameOver(GameData gameData, Player performingClient, GameItem it) {
        this.finalizing = false;
        this.coinPaid = false;
        this.outItem = it;
        buildContent(performingClient, gameData);
    }
}
