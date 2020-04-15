package comm.chat;

import model.*;
import util.HTMLText;

public class OverRadioSayChatHandler extends ChatCommandHandler {
    public OverRadioSayChatHandler() {
        super("rinsay");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String whatWasSaid = rest.replace("/rinsay ", "");
        if (gameData.getGameState() != GameState.PRE_GAME) {
            if (sender.isDead()) {
                gameData.getChat().serverInSay("You try to talk, but you're dead, so you only manage to cause some static on the radio.",
                        sender);
                gameData.getChat().serverInSay(HTMLText.makeText("blue", "Bzzzzbzzzz!"));
            } else {
                gameData.getChat().overRadioSay(gameData, sender, whatWasSaid);
                Talking.decorateWithTalk(gameData, sender, whatWasSaid);
            }
        }
    }
}
