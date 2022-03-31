package model.plebOS.klondike;

import model.plebOS.KlondikeCommand;

import java.awt.*;

public class LongKlondikePile extends PlayableKlondikePile {
    public LongKlondikePile(int xpos, int ypos) {
        super(true, xpos, ypos);
    }

    @Override
    protected boolean canBePlayedOn(KlondikeCard selected, KlondikeCard top) {
        return selected.isLessAndOppositeSuit(top);
    }

    @Override
    public boolean handleClick(KlondikeCommand klondikeCommand, int x, int y) {
        KlondikeCard selected = klondikeCommand.getSelectedCard();
        if (selected == null) {
            int cardIndex = multiselect(x, y);
            if (cardIndex >= 0) {
                if (top() != null && top() == this.get(cardIndex) && !top().isRevealed()) {
                    top().flip();
                }
                Rectangle hitBox = getHitBox(cardIndex);
                hitBox.height += (this.size() - cardIndex - 1) * ROW_HEIGHT;
                klondikeCommand.selectCard(this.get(cardIndex), hitBox);
                return true;
            } else {
                return false;
            }
        } else if (super.isClicked(x, y) && canBePlayedOn(selected, top())) {
            klondikeCommand.moveCards(selected, this);
        }
        return false;
    }

    private int multiselect(int x, int y) {
        int cardIndex = size() - 1;
        for (; cardIndex >= 0; cardIndex--) {
            if (getHitBox(cardIndex).contains(new Point(x, y))) {
                break;
            }
        }
        return cardIndex;
    }
}
