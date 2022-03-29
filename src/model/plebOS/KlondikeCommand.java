package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.fancyframe.KlondikeFancyFrame;
import model.plebOS.klondike.KlondikeCard;
import model.plebOS.klondike.KlondikeDeck;

import java.awt.*;
import java.awt.image.BufferedImage;

public class KlondikeCommand extends PlebOSCommandHandler {

    public static final int GAME_WIDTH = 285;
    public static final int GAME_HEIGHT = 215;

    private KlondikeCard card = new KlondikeCard(10, "c", 7, 3);
    private KlondikeDeck deck = new KlondikeDeck();

    public KlondikeCommand() {
        super("klondike");
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
        card.drawYourself(g, 100, 100);
        deck.drawYourself(g, 2, 2);
        return img;
    }
}
