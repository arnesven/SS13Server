package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientsound.ClientSoundManager;
import clientview.GameModePanel;
import main.SS13Client;
import shared.SoundManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;

public class LobbyView extends JPanel {

    private final String clid;
    private final PlayersAndChatPanel pacp;
    private final GameModePanel settings;
    private final JobsPanel jobs;
    private final SummaryPanel summary;
    private final SS13Client parent;
    private JTabbedPane tlp;
    private ServerInfoPanel serverInfoPanel;
    //   private final ChatPanel cp;

    public LobbyView(String username, SS13Client parent) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            tlp = new JTabbedPane();
            this.clid = username;
            this.parent = parent;

        serverInfoPanel = new ServerInfoPanel();
        tlp.add(serverInfoPanel, "Info");
        getServerInfo();
        tlp.setSelectedIndex(0);

        jobs = new JobsPanel(username, this);
        //JScrollPane jsp = new JScrollPane(jobs);
        tlp.add(jobs, "Jobs");

        //tlp.add(new PlayersPanel(username), "Players");
        pacp = new PlayersAndChatPanel(username, parent);
        tlp.add(pacp, "Players");

        StylePanel sp = new StylePanel(username, parent);
        tlp.add(sp, "Style");

        settings = new GameModePanel(username);
        tlp.add(settings, "Modes");
        summary = new SummaryPanel();
        tlp.add(summary, "Summary");

        tlp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if (index == 3) {
                    sp.load();
                } else if (index == 1) {
                    jobs.load();
                } else if (index == 4) {
                    settings.load();
                }
            }
        });

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
                    //Desktop.getDesktop().browse(file.toURI());
                    serverInfoPanel.addContent(GameData.getInstance().decodeBase64Images(result));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail() {
                System.out.println("Failed to send INFOGET message to server");
            }
        });


    }


    public void flipToSummary() {
        tlp.setSelectedIndex(5);
    }


    public PlayersAndChatPanel getPlayersPanel() {
        return pacp;
    }

    public void unregisterYourSubscribers() {
        settings.unregisterYourself();
        jobs.unregisterYourself();
        summary.unregisterYourself();
        getPlayersPanel().unregisterYourSubscribers();
    }
}
