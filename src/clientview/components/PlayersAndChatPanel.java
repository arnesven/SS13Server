package clientview.components;

import clientview.PlayersPanel;

import javax.swing.*;
import java.awt.*;

public class PlayersAndChatPanel extends JPanel {
    public PlayersAndChatPanel(String username) {
        PlayersPanel players = new PlayersPanel(username);
        ChatPanel chat = new ChatPanel();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(players);
        this.add(chat);
    }
}
