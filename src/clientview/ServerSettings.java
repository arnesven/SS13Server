package clientview;

import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerSettings extends JPanel implements Observer {

    private final TextField roundsBox;
    private final JComboBox<String> modeBox;
    private  Box playerSettingsPanel;

    public ServerSettings(String username) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	this.add(new JLabel("Server Settings"));
    {
        this.setAlignmentX(0.0f);
        Box hp = new Box(BoxLayout.X_AXIS);
        roundsBox = new TextField();
        roundsBox.setMaximumSize(new Dimension(100, 25));
        roundsBox.setColumns(50);

        hp.add(new JLabel("No of rounds: "));
        hp.add(roundsBox);

        roundsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("ACTIONPERFORMED");
                try {
                    int num = Integer.parseInt(roundsBox.getText());
                    if (num > 0) {
                        GameData.getInstance().setNoOfRounds(num);
                    }

                } catch (NumberFormatException nfe) {

                }

            }
        });

        this.add(hp);
    }

    {
        Box hp = new Box(BoxLayout.X_AXIS);
        hp.add(new JLabel("Game mode: "));
        modeBox = new JComboBox<String>();
        modeBox.addActionListener((ActionEvent e) -> {
            if (modeBox.getSelectedItem() != null) {
                if (modeBox.isEnabled()) {
                    if (!modeBox.getSelectedItem().equals(GameData.getInstance().getSelectedMode())) {
                        GameData.getInstance().setSelectedMode((String) (modeBox.getSelectedItem()));
                    }
                }
            }
        });
        modeBox.setMaximumSize(new Dimension(150, 25));
        //modeBox.getElement().getStyle().setFontSize(14, Unit.PT);


        hp.add(modeBox);


        this.add(hp);
    }


        {
            Box hp = new Box(BoxLayout.X_AXIS);
            hp.add(new JLabel("Player Settings:"));

            playerSettingsPanel = new Box(BoxLayout.Y_AXIS);
            playerSettingsPanel.setAlignmentX(-1.0f);

            hp.add(playerSettingsPanel);
            this.add(hp);
        }

        this.add(Box.createVerticalGlue());

        //GameData.getInstance().sendSettings();
        GameData.getInstance().subscribe(this);
    }


    @Override
    public void update() {

        roundsBox.setText(GameData.getInstance().getNoOfRounds()+"");

        if (!GameData.getInstance().getSelectedMode().equals(modeBox.getSelectedItem())) {
            modeBox.setEnabled(false);
            modeBox.removeAllItems();
            int i = 0;
            int ind = 0;
            for (String s : GameData.getInstance().getModeAlternatives()) {
                modeBox.addItem(s);
                if (s.equals(GameData.getInstance().getSelectedMode())) {
                    ind = i;
                }
                i++;
            }
            modeBox.setSelectedIndex(ind);
            modeBox.setEnabled(true);
        }

        playerSettingsPanel.setEnabled(false);
        playerSettingsPanel.removeAll();

        int indy = 0;
        for (final String str : GameData.getInstance().getSettingNames()) {
            final JCheckBox box = new JCheckBox(str);

            box.setSelected(GameData.getInstance().getSettingBools().get(indy));
            box.addActionListener(new ActionListener(){
                private JCheckBox b = box;
                private String setName = str;
                @Override
                public void actionPerformed(ActionEvent event) {
                    GameData.getInstance().setPlayerSetting(setName, b.isSelected());

                }
            });

            indy++;
            playerSettingsPanel.add(box);
            playerSettingsPanel.setEnabled(true);
        }

    }


}
