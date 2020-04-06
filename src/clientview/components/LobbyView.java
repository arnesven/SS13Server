package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.PlayersPanel;
import clientview.ServerSettings;
import main.SS13Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LobbyView extends JPanel {

    private final String clid;
    private final PlayersAndChatPanel pacp;
    private JTabbedPane tlp;
 //   private final ChatPanel cp;

    public LobbyView(String username, SS13Client parent) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            tlp = new JTabbedPane();
            this.clid = username;

        tlp.add(new JobsPanel(username), "Jobs");

        //tlp.add(new PlayersPanel(username), "Players");
        pacp = new PlayersAndChatPanel(username, parent);
        tlp.add(pacp, "Players");
        tlp.setSelectedIndex(1);

        getServerInfo();

        tlp.add(new ServerSettings(username), "Settings");
        tlp.add(new SummaryPanel(), "Summary");

        this.add(tlp);
    }

    private void getServerInfo() {
        ServerCommunicator.send(clid + " INFOGET", new MyCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    File file = File.createTempFile("last_server_info", ".html");
                    FileWriter writer = new FileWriter(file);
                    writer.write(result);
                    writer.close();
                    Desktop.getDesktop().browse(file.toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void flipToSummary() {
        tlp.setSelectedIndex(2);
       // tlp.selectTab(2);

    }


    public PlayersAndChatPanel getPlayersPanel() {
        return pacp;
    }
}
