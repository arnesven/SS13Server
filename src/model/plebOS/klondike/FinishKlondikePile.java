package model.plebOS.klondike;

import java.awt.*;

public class FinishKlondikePile extends PlayableKlondikePile {
    public FinishKlondikePile(int xpos, int ypos) {
        super(false, xpos, ypos);
    }

    @Override
    public void drawYourself(Graphics2D g) {
        super.drawYourself(g);
        g.setColor(new Color(0, 80, 0));
        Rectangle r = getHitBox();
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    @Override
    protected boolean canBePlayedOn(KlondikeCard selected, KlondikeCard top) {
        return selected.isMoreAndIsSameSuit(top);
    }
}
