package clientview.dialogs;

import main.SS13ServerMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNewServerDialog extends JDialog  {
    private final JTextField serverName;
    private final JTextField serverPort;
    private final JRadioButton tryToRecover;
    private final JRadioButton removeOldData;
    private final JCheckBox alsoConnect;
    private boolean started = false;

    public StartNewServerDialog(JFrame parent) {
        super(parent, "Start Local Server", true);
        this.setLayout(new BorderLayout());
        Box southPanel = new Box(BoxLayout.X_AXIS);
        this.add(southPanel, BorderLayout.SOUTH);
        this.setSize(new Dimension(250, 170));
        this.setLocationRelativeTo(parent);
        JButton start = new JButton("Start");
        JButton cancel = new JButton("Cancel");
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(cancel);
        southPanel.add(Box.createHorizontalStrut(5));
        southPanel.add(start);
        southPanel.add(Box.createHorizontalGlue());
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SS13ServerMain(Integer.parseInt(serverPort.getText()),
                        serverName.getText(),
                        true,
                        removeOldData.isSelected()).start();
                started = true;
                StartNewServerDialog.this.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        Box midPanel = new Box(BoxLayout.Y_AXIS);
        midPanel.add(Box.createVerticalGlue());
        {
            Box fieldBox = new Box(BoxLayout.X_AXIS);
            fieldBox.add(new JLabel("Server name: "));
            this.serverName = new JTextField("Donut SS13");
            fieldBox.add(serverName);
            midPanel.add(fieldBox);
        }
        {
            Box fieldBox = new Box(BoxLayout.X_AXIS);
            fieldBox.add(new JLabel("Port: "));
            this.serverPort = new JTextField("55444");
            fieldBox.add(serverPort);
            midPanel.add(fieldBox);
        }
        ButtonGroup grp = new ButtonGroup();
        {
            Box fieldBox = new Box(BoxLayout.X_AXIS);
            fieldBox.add(new JLabel("Try to recover old game: "));
            this.tryToRecover = new JRadioButton("");
            this.tryToRecover.setSelected(true);
            grp.add(tryToRecover);
            fieldBox.add(this.tryToRecover);
            midPanel.add(fieldBox);
        }
        {
            Box fieldBox = new Box(BoxLayout.X_AXIS);
            fieldBox.add(new JLabel("Remove old game data: "));
            this.removeOldData = new JRadioButton("");
            fieldBox.add(removeOldData);
            grp.add(removeOldData);
            midPanel.add(fieldBox);
        }
        {
            Box fieldBox = new Box(BoxLayout.X_AXIS);
            fieldBox.add(new JLabel("Also connect me as client: "));
            this.alsoConnect = new JCheckBox("", true);
            fieldBox.add(alsoConnect);
            midPanel.add(fieldBox);
        }

        midPanel.add(Box.createVerticalGlue());
        this.add(midPanel);

        this.setVisible(true);
    }

    public boolean didStart() {
        return started;
    }

    public boolean alsoConnectMe() {
        return alsoConnect.isSelected();
    }

    public String getHost() {
        return serverName.getText();
    }

    public int getPort() {
        return Integer.parseInt(serverPort.getText());
    }
}
