package model.fancyframe;

import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;
import util.HTMLText;

public abstract class AIConsoleFancyFrame extends ConsoleFancyFrame {
    public AIConsoleFancyFrame(FancyFrame old, Console console, GameData gameData, String bgColor, String fgColor) {
        super(old, console, gameData, bgColor, fgColor);
    }


    protected String addGreeting(GameData gameData, AIConsole console, Player player) {
        String name = "there.";
        if (player.getCharacter().isCrew()) {
            try {
                name = gameData.getClidForPlayer(player) + ".";
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
        return HTMLText.makeCentered(
                HTMLText.makeImage(console.getScreen().getSprite(player)) +
                        "<br/><i>\"Hello " + name + "<br/>What can I do for you?\"</i><br/><br/>" );
    }
}
