package model.plebOS.klondike;

import model.plebOS.KlondikeCommand;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class PlayableKlondikePile extends KlondikePile {
    public PlayableKlondikePile(boolean revealed, int xpos, int ypos) {
        super(revealed, xpos, ypos);
    }

    @Override
    public void drawYourself(Graphics2D g) {
        super.drawYourself(g);

        if (!isRevealed()) {
            g.setColor(new Color(0, 50, 0));
            Rectangle r = getHitBox();
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    @Override
    public boolean handleClick(KlondikeCommand klondikeCommand, int x, int y) {
        KlondikeCard selected = klondikeCommand.getSelectedCard();
        if (selected == null) {
            return super.handleClick(klondikeCommand, x, y);
        }
        if (isClicked(x, y)) {
            if ((isRevealed() && selected.isLessAndOppositeSuit(top())) ||
                    (!isRevealed() && selected.isMoreAndIsSameSuit(top()))) {
                klondikeCommand.removeCard(selected);
                this.add(selected);
                klondikeCommand.unselectCard();
            }
            return true;
        }
        return false;
    }
}
