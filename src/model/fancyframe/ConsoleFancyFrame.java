package model.fancyframe;

import model.GameData;
import model.Player;
import model.objects.consoles.Console;

public abstract class ConsoleFancyFrame extends FancyFrame {

    private final Console console;

    public ConsoleFancyFrame(FancyFrame old, Console console) {
        super(old);
        this.console = console;
    }


    public void leaveFancyFrame(GameData gameData, Player pl) {
        pl.setFancyFrame(new FancyFrame(this));
    }

    @Override
    protected void beingDisposed() {
        console.setFancyFrameVacant();
    }

    public abstract void rebuildInterface(GameData gameData, Player player);
}
