package comm.chat;

import model.GameData;
import model.GameState;
import model.Player;
import model.PlayerSettings;

public class StyleChatHandler extends ChatCommandHandler {
    public StyleChatHandler() {
        super("style");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String rest2 = rest.replace("/style ", "");
        if (gameData.getGameState() == GameState.MOVEMENT && rest2.equals("on")) {
            gameData.getChat().serverSay("Style customization can only be turned on during action phase.");
        } else {
            sender.getSettings().set(PlayerSettings.STYLE_BUTTONS_ON, rest2.equals("on"));
            if (rest2.equals("on")) {
                gameData.getChat().serverSay( "Enabled style customization. Turn off with /style off");
            } else {
                gameData.getChat().serverSay("Disabled style customization. Turn on with /style on");
            }
        }
    }
}
