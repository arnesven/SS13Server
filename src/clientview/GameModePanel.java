package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.ModePanel;
import model.modes.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GameModePanel extends JPanel implements Observer {

    private final JPanel innerPanel;
    private final GridLayout grid;
    private JTextField timeLimitField;
    private JTextField roundField;
    private boolean loaded = false;
    private Map<String, ModePanel> modePanels;
    private int noOfRounds;
    private int timeLimit;

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
            titleBox.add(new JLabel("Round Time Limit:"));
            makeTimeLimitField();
            titleBox.add(timeLimitField);
            this.add(titleBox, BorderLayout.NORTH);
        }

    	innerPanel = new JPanel();
        this.grid = new GridLayout(5, 2);
        innerPanel.setLayout(grid);
    	this.add(innerPanel);
    //GameData.getInstance().sendSettings();
        GameData.getInstance().subscribe(this);
    }

    private void makeRoundField() {
        roundField = new JTextField("??");
        roundField.setColumns(3);
        roundField.setMaximumSize(new Dimension(30, 30));
        roundField.setMinimumSize(new Dimension(30, 30));
        roundField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int rounds = Integer.parseInt(roundField.getText());
                    sendSettings(rounds, Integer.parseInt(timeLimitField.getText()), GameData.getInstance().getSelectedMode());
                    noOfRounds = rounds;
                } catch (NumberFormatException nfe) {
                    roundField.setText(GameData.getInstance().getNoOfRounds() + "");
                }
            }
        });
    }

    private void makeTimeLimitField() {
        timeLimitField = new JTextField("??");
        timeLimitField.setColumns(5);
        timeLimitField.setMaximumSize(new Dimension(30, 30));
        timeLimitField.setMinimumSize(new Dimension(30, 30));
        timeLimitField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int timeLimit = Integer.parseInt(timeLimitField.getText());
                    sendSettings(Integer.parseInt(roundField.getText()), timeLimit, GameData.getInstance().getSelectedMode());
                    GameModePanel.this.timeLimit = timeLimit;
                } catch (NumberFormatException nfe) {
                    timeLimitField.setText(GameData.getInstance().getTimeLimit() + "");
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
        if (GameData.getInstance().getTimeLimit() != timeLimit) {
            timeLimitField.setText(GameData.getInstance().getTimeLimit() + "");
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
        timeLimit = Integer.parseInt(parts[1]);
        timeLimitField.setText(parts[1]);
        String selectedMode = parts[2];
        int noOfModes = Integer.parseInt(parts[3]);
        ButtonGroup bg = new ButtonGroup();
        for (int i = 4; i < noOfModes*2+3; i+=2) {
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
            if (modePanels.size() > grid.getColumns()*grid.getRows()) {
                grid.setRows(grid.getRows()+1);
            }
            innerPanel.add(mp);
        }

        for (int i = noOfModes*2+4; i < parts.length; i += 2) {
            modePanels.get(parts[i]).setProbability(Double.parseDouble(parts[i+1]));
        }

        modePanels.get(selectedMode).getCheckBox().setSelected(true);
        for (String mode : modePanels.keySet()) {
            modePanels.get(mode).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                   sendSettings(GameData.getInstance().getNoOfRounds(), Integer.parseInt(timeLimitField.getText()), mode);
                }});

        }

        revalidate();
        repaint();
    }

    private void sendSettings(int rounds, int timeLimit, String mode) {
        ServerCommunicator.send(GameData.getInstance().getClid() + " SETTINGS " + rounds + "<player-data-part>"
                + mode + "<player-data-part>" + timeLimit, new MyCallback<String>() {

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
