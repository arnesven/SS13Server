package comm.chat;

import model.GameData;
import model.Player;
import sounds.FartSound;

public class FartChatHandler extends ChatCommandHandler {
    public FartChatHandler() {
        super("fart");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        if (sender.getCharacter() != null) {
            for (Player p : sender.getCharacter().getPosition().getClients()) {
                if (p != sender) {
                    gameData.getChat().serverInSay(sender.getPublicName(p) + " farted.", p);
                } else {
                    gameData.getChat().serverInSay("You farted.", p);
                }
                p.getSoundQueue().add(new FartSound());
            }
        }
    }
}
