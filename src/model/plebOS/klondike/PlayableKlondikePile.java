package model.plebOS.klondike;

import model.plebOS.KlondikeCommand;
import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class PlayableKlondikePile extends KlondikePile {
    public PlayableKlondikePile(boolean drawLong, int xpos, int ypos) {
        super(drawLong, xpos, ypos);
    }

    protected abstract boolean canBePlayedOn(KlondikeCard selected, KlondikeCard top);

    @Override
    public boolean handleClick(KlondikeCommand klondikeCommand, int x, int y) {
        if (!isClicked(x, y)) {
            return false;
        }
        KlondikeCard selected = klondikeCommand.getSelectedCard();
        if (selected == null) {
            return super.handleClick(klondikeCommand, x, y);
        }
        if (canBePlayedOn(selected, top())) {
            klondikeCommand.moveCards(selected, this);
        }
        return true;
    }

}
