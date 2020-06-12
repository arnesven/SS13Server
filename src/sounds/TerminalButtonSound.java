package sounds;

import util.MyRandom;

public class TerminalButtonSound extends Sound {
    public TerminalButtonSound() {
        super("terminal_button0" + (MyRandom.nextInt(8)+1));
    }
}
