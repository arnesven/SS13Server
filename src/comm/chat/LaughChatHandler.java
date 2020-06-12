package comm.chat;

import model.GameData;
import model.Player;
import sounds.Sound;

public class LaughChatHandler extends ChatCommandHandler {
    public LaughChatHandler() {
        super("laugh");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        if (sender.getCharacter() != null && sender.getCharacter().getSoundSet().hasLaughSound()) {
            Sound laugh = sender.getCharacter().getSoundSet().getLaughSound();
            for (Player p : sender.getPosition().getClients()) {
                p.getSoundQueue().add(laugh);
            }
        }
    }
}
