package clientview.components;

import clientview.PlayersPanel;
import main.SS13Client;

import javax.swing.*;
import java.awt.*;

public class PlayersAndChatPanel extends JPanel {
    private final PlayersPanel players;

    public PlayersAndChatPanel(String username, SS13Client parent) {
        players = new PlayersPanel(username, parent);
        ChatPanel chat = new ChatPanel();
        chat.setPreferredSize(new Dimension((parent.getWidth() * 2) / 3, parent.getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(players);
        this.add(chat);
    }

}
