package model.fancyframe;

import model.Player;
import model.objects.consoles.GeneratorConsole;
import util.HTMLText;
import util.MyStrings;

public class PowerGeneratorFancyFrame extends FancyFrame {
    private final GeneratorConsole console;

    public PowerGeneratorFancyFrame(GeneratorConsole console, Player player) {
        this.console = console;
        super.setState(player.getFancyFrame().getState());

        String prios = MyStrings.join(console.getSource().getPrios(), ", ");

        setData(console.getPublicName(player), false,
                HTMLText.makeColoredBackground("#837534",
                HTMLText.makeText("yellow",
                HTMLText.makeFancyFrameLink("LOGIN", "[log in]") + "<br/>" +
                HTMLText.makeCentered("<h4>Power Status</h4><br/>") +
                        "Current Power Output: " + String.format("%.1f MW", console.getSource().getPowerLevel()) + "<br/>" +
                        "Power Priority: " + prios
                )));
    }

    @Override
    public void handleEvent(String event) {
        if (event.contains("DISMISS")) {
            console.setFancyFrameVacant();
            super.handleEvent(event);
        }
    }
}
