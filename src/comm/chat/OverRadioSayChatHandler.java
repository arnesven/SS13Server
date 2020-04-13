package comm.chat;

import model.GameData;
import model.GameState;
import model.Player;
import model.Talking;

public class OverRadioSayChatHandler extends ChatCommandHandler {
    public OverRadioSayChatHandler() {
        super("rinsay");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String whatWasSaid = rest.replace("/rinsay ", "");
        if (gameData.getGameState() != GameState.PRE_GAME) {
            gameData.getChat().overRadioSay(gameData, sender, whatWasSaid);
            Talking.decorateWithTalk(gameData, sender, whatWasSaid);
        }
    }
}
