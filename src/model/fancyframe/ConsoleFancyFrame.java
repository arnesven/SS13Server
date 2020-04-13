package model.fancyframe;

import model.GameData;
import model.Player;
import model.objects.consoles.Console;

public class ConsoleFancyFrame extends FancyFrame {

    private final Console console;

    public ConsoleFancyFrame(FancyFrame old, Console console) {
        super(old);
        this.console = console;
    }


    public void leaveFancyFrame(GameData gameData, Player pl) {
        console.setFancyFrameVacant();
        pl.setFancyFrame(new FancyFrame(this));
    }
}
