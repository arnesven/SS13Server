package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.ModePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GameModePanel extends JPanel implements Observer {

    private final Box innerPanel;
    private JTextField roundField;
    private boolean loaded = false;
    private Map<String, ModePanel> modePanels;
    private int noOfRounds;

    public GameModePanel(String username) {
        this.setLayout(new BorderLayout());
        modePanels = new HashMap<>();

        {
            Box titleBox = new Box(BoxLayout.X_AXIS);
            JLabel title = new JLabel("Game Modes");
            title.setFont(new Font("Arial", Font.ITALIC, 22));
            titleBox.add(title);
            titleBox.add(Box.createHorizontalGlue());
            titleBox.add(new JLabel("Round Limit:"));
            makeRoundField();
            titleBox.add(roundField);
            this.add(titleBox, BorderLayout.NORTH);
        }

    	innerPanel = new Box(BoxLayout.Y_AXIS);
    	this.add(innerPanel);
    //GameData.getInstance().sendSettings();
        GameData.getInstance().subscribe(this);
    }

    private void makeRoundField() {
        roundField = new JTextField("??");
        roundField.setColumns(5);
        roundField.setMaximumSize(new Dimension(30, 30));
        roundField.setMinimumSize(new Dimension(30, 30));
        roundField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int rounds = Integer.parseInt(roundField.getText());
                    sendSettings(rounds, GameData.getInstance().getSelectedMode());
                    noOfRounds = rounds;
                } catch (NumberFormatException nfe) {
                    roundField.setText(GameData.getInstance().getNoOfRounds() + "");
                }
            }
        });

    }

    public void load() {
        if (!loaded) {
            loaded = true;
            ServerCommunicator.send(GameData.getInstance().getClid() + " MODES LOAD", new MyCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    addModes(result);
                }

                @Override
                public void onFail() {
                    System.out.println("Failed to send MODES LOAD message to server");
                }
            });
        }
    }


    @Override
    public void update() {
        if (GameData.getInstance().getNoOfRounds() != noOfRounds) {
            roundField.setText(GameData.getInstance().getNoOfRounds() + "");
        }
        ModePanel mp = modePanels.get(GameData.getInstance().getSelectedMode());
        if (mp != null) {
            mp.getCheckBox().setSelected(true);
        }

    }


    private void addModes(String result) {
        System.out.println("Result is: " + result);
        String[] parts = result.split("<modes-part>");
        noOfRounds = Integer.parseInt(parts[0]);
        roundField.setText(parts[0]);
        String selectedMode = parts[1];
        int noOfModes = Integer.parseInt(parts[2]);
        ButtonGroup bg = new ButtonGroup();
        for (int i = 3; i < noOfModes*2+3; i+=2) {
            boolean enabled;
            if (selectedMode.equals("Secret")) {
                if (parts[i].equals("Creative")) {
                    enabled = false;
                } else {
                    enabled = true;
                }
            } else {
                enabled = parts[i].equals(selectedMode);
            }
            ModePanel mp = new ModePanel(parts[i], parts[i+1], enabled, bg);
            modePanels.put(parts[i], mp);
            innerPanel.add(mp);
        }

        for (int i = noOfModes*2+3; i < parts.length; i += 2) {
            modePanels.get(parts[i]).setProbability(Double.parseDouble(parts[i+1]));
        }

        modePanels.get(selectedMode).getCheckBox().setSelected(true);
        for (String mode : modePanels.keySet()) {
            modePanels.get(mode).getCheckBox().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                   sendSettings(GameData.getInstance().getNoOfRounds(), mode);
                }});

        }

        revalidate();
        repaint();
    }

    private void sendSettings(int rounds, String mode) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " SETTINGS " + rounds + "<player-data-part>" + mode, new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail() {
                System.out.println("Failed to send SETTINGS message to server");
            }
        });
    }


    @Override
    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
    }

}
