package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.fancyframe.KlondikeFancyFrame;
import model.plebOS.klondike.KlondikeCard;
import model.plebOS.klondike.KlondikeDeck;
import model.plebOS.klondike.KlondikePile;
import model.plebOS.klondike.PlayableKlondikePile;
import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KlondikeCommand extends PlebOSCommandHandler {

    public static final int GAME_WIDTH = 290;
    public static final int GAME_HEIGHT = 215;
    private static final int MARGIN_X = 2;
    private static final int MARGIN_Y = 2;
    private static final int PLAYSTACKS_Y_OFFSET = 45;
    private static final int COLUMN_WIDTH = 30;

    private KlondikeDeck deck = new KlondikeDeck(MARGIN_X, MARGIN_Y);
    private KlondikePile drawPile = new KlondikePile(false, COLUMN_WIDTH + MARGIN_X, MARGIN_Y);
    private List<PlayableKlondikePile> playStacks = new ArrayList<>();
    private List<PlayableKlondikePile> suitStacks = new ArrayList<>();
    private KlondikeCard selectedCard;
    private Rectangle selectedBox;

    public KlondikeCommand() {
        super("klondike");
        for (int i = 0; i < 7; i++) {
            playStacks.add(new PlayableKlondikePile(true, MARGIN_X + i*COLUMN_WIDTH, PLAYSTACKS_Y_OFFSET));
            for (int j = 0; j < i; j++) {
                playStacks.get(i).add(deck.pop());
            }
            KlondikeCard c = deck.pop();
            c.flip();
            playStacks.get(i).add(c);
        }
        for (int i = 0; i < 4; i++) {
            suitStacks.add(new PlayableKlondikePile(false, MARGIN_X + (3+i) * COLUMN_WIDTH, MARGIN_Y));
        }

    }

    @Override
    protected boolean doesReadyUser() { return true; }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance) {
        sender.setFancyFrame(new KlondikeFancyFrame(gameData, sender));
    }

    public BufferedImage makeImageFromContents() {
        BufferedImage img = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(new Color(0, 128, 0));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        deck.drawYourself(g);
        drawPile.drawYourself(g);
        for (KlondikePile playStack : getAllStacks()) {
            playStack.drawYourself(g);
        }
        if (gameIsWon()) {
            g.setColor(new Color(255, 255, 100));
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("CONGRATULATIONS!", 50, 70);
            g.drawString("YOU WIN!", 100, 90);
        } else if (selectedCard != null) {
            g.setColor(new Color(155, 225, 255));
            g.drawRect(selectedBox.x, selectedBox.y, selectedBox.width, selectedBox.height);
        }
        return img;
    }

    private boolean gameIsWon() {
        for (KlondikePile p : suitStacks) {
            if (p.size() != 13) {
                return false;
            }
        }
        return true;
    }

    public void handleClick(int x, int y) {
        if (gameIsWon()) {
            return;
        }
        if (deck.handleClick(this, x, y)) {
            return;
        }

        if (drawPile.handleClick(this, x, y)) {
            return;
        }

        for (KlondikePile stack : getAllStacks()) {
            if (stack.handleClick(this, x, y)) {
                return;
            }
        }
        unselectCard();
    }

    private List<PlayableKlondikePile> getAllStacks() {
        List<PlayableKlondikePile> allStacks = new ArrayList<>();
        allStacks.addAll(playStacks);
        allStacks.addAll(suitStacks);
        return allStacks;
    }

    public void unselectCard() {
        selectedCard = null;
        selectedBox = null;
        Logger.log("KLONDIKE: Card unselected.");
    }

    public void selectCard(KlondikeCard top, Rectangle hitBox) {
        if (top == null) {
            unselectCard();
        } else {
            Logger.log("KLONDIKE: " + top.getName() + " selected.");
            selectedCard = top;
            selectedBox = hitBox;
        }
    }

    public KlondikePile getDrawPile() {
        return drawPile;
    }

    public KlondikeCard getSelectedCard() {
        return selectedCard;
    }

    public void removeCard(KlondikeCard selected) {
        if (selected == drawPile.top()) {
            drawPile.pop();
            return;
        }
        for (KlondikePile p : getAllStacks()) {
            if (selected == p.top()) {
                p.pop();
                return;
            }
        }
    }
}
