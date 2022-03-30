package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.fancyframe.KlondikeFancyFrame;
import model.plebOS.klondike.KlondikeCard;
import model.plebOS.klondike.KlondikeDeck;
import model.plebOS.klondike.KlondikePile;
import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    private List<KlondikePile> playStacks = new ArrayList<>();
    private List<KlondikePile> suitStacks = new ArrayList<>();
    private KlondikeCard selectedCard;
    private Rectangle selectedBox;

    public KlondikeCommand() {
        super("klondike");
        for (int i = 0; i < 7; i++) {
            playStacks.add(new KlondikePile(true, MARGIN_X + i*COLUMN_WIDTH, PLAYSTACKS_Y_OFFSET));
            for (int j = 0; j < i; j++) {
                playStacks.get(i).add(deck.pop());
            }
            KlondikeCard c = deck.pop();
            c.flip();
            playStacks.get(i).add(c);
        }
        for (int i = 0; i < 4; i++) {
            suitStacks.add(new KlondikePile(false, MARGIN_X + (3+i) * COLUMN_WIDTH, MARGIN_Y));
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
        for (KlondikePile playStack : playStacks) {
            playStack.drawYourself(g);
        }
        for (KlondikePile suitStack : suitStacks) {
            suitStack.drawYourself(g);
        }
        if (selectedCard != null) {
            g.setColor(new Color(10, 100, 255));
            g.drawRect(selectedBox.x, selectedBox.y, selectedBox.width, selectedBox.height);
            Logger.log("KLONDIKE: Drawing selected card!");
        } else {
            Logger.log("KLONDIKE: Selected card was null");
        }
        return img;
    }

    public void handleClick(int x, int y) {
        if (deck.isClicked(x, y)) {
            Logger.log("KLONDIKE: Deck clicked");
            unselectCard();
            return;
        } else if (drawPile.isClicked(x, y)) {
            Logger.log("KLONDIKE: Draw Pile clicked");
            selectCard(drawPile.top(), drawPile.getHitBox());
            return;
        }
        for (KlondikePile stack : playStacks) {
            if (stack.isClicked(x, y)) {
                int i = playStacks.indexOf(stack);
                KlondikeCard card = stack.top();
                Logger.log("KLONDIKE: Playstack " + i + "clicked, selecting " + card.getName());
                selectCard(card, stack.getHitBox());
                return;
            }
        }
        for (KlondikePile stack : suitStacks) {
            if (stack.isClicked(x, y)) {
                int i = suitStacks.indexOf(stack);
                Logger.log("KLONDIKE: Suitstack " + i + "clicked");
                return;
            }
        }
        unselectCard();
    }

    private void unselectCard() {
        selectedCard = null;
        selectedBox = null;
    }

    private void selectCard(KlondikeCard top, Rectangle hitBox) {
        selectedCard = top;
        selectedBox = hitBox;
    }
}
