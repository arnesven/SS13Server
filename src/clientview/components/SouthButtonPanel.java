package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import main.SS13Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SouthButtonPanel extends Box implements Observer {


    public static final int HEIGHT = 24;
    public static final int HEIGHT_PIXELS = 34;
    public static final int LOBBY = 1;
    private final JLabel clidLabel;
    private final JLabel gameStateLabel;
    private final JLabel clientVersionLabel;
    // private final JToggleButton mapButton;

    private boolean ready = false;
    private boolean inLobby = true;
    private final GameUIPanel parent;
    private JButton readyButton;
    private JToggleButton gameButton;
//    private ToggleButton mapButton;
//    private Label clidLabel;
//    private Label gameStateLabel;
    private JLabel roundNumberLabel = new JLabel("0");

    public SouthButtonPanel(final GameUIPanel parent) {
        super(BoxLayout.X_AXIS);
        this.parent = parent;

          gameButton = new JToggleButton("Game");
          gameButton.setPreferredSize(new Dimension(100, gameButton.getPreferredSize().height));
          this.add(gameButton);
        gameButton.setEnabled(false);
        gameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                SouthButtonPanel.this.toggleView();
                parent.getFrame().requestFocus();
            }
        });
//
          readyButton = new JButton("READY");


        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
               readyButtonPressed();
               parent.getFrame().requestFocus();
            }
        });
        readyButton.setBackground(new Color(0x999999));
        readyButton.setPreferredSize(new Dimension(200, 0));
//
//        mapButton = new JToggleButton("MAP");
//        this.add(mapButton);
        this.add(readyButton);


//        mapButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                parent.toggleMapView();
//            }
//        });

            clidLabel = new JLabel(GameData.getInstance().getClid());
            this.add(clidLabel);

          gameStateLabel = new JLabel("?");
          this.add(new JLabel(", "));
          this.add(gameStateLabel);

          clientVersionLabel = new JLabel("..version?..");
          clientVersionLabel.setForeground(Color.RED);
          this.add(Box.createHorizontalGlue());
          this.add(clientVersionLabel);

//        HorizontalPanel hp = new HorizontalPanel();
         this.add(Box.createHorizontalGlue());
          this.add(new JLabel("Round: "));

        this.add(roundNumberLabel);
        this.add(Box.createHorizontalStrut(5));

        GameData.getInstance().subscribe(this);

    }

    public void readyButtonPressed() {
        SouthButtonPanel.this.toggleReady();

        sendToServerReady(ready);
    }


    protected void sendToServerReady(boolean ready2) {
        String message = "READY";
        if (ready) {
            message += " YES";
        } else {
            message += " NO";
        }
//
        ServerCommunicator.send(parent.getUsername() + " " + message, new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                GameData.getInstance().deconstructReadyListAndStateAndRoundAndSettings(result);
            }
        });
    }

    protected void toggleReady() {
        if (ready) {
            readyButton.setBackground(new Color(0x999999));
            //readyButton.getElement().getStyle().setColor("#000000");
        } else {
            readyButton.setBackground(new Color(0x00FF00));
            //readyButton.getElement().getStyle().setColor("#00FF00");
        }
        ready = !ready;
    }

    public void setUnready() {
        //readyButton.setDown(false);
        if (ready) {
            toggleReady();
        }
    }

    private void toggleView() {
        parent.toggleView();
        toggleViewButton();
    }



    public void toggleViewButton() {
        if (inLobby) {
            gameButton.setText("Lobby");
        } else {
            gameButton.setText("Game");
        }
        inLobby = !inLobby;
    }
//
//
//
    @Override
    public void update() {
        clidLabel.setText(GameData.getInstance().getClid());
        String state = "Game Over";
        if (GameData.getInstance().getState() > 0) {
            if (GameData.getInstance().amISpectator()) {
                state = "[Spectating] ";
            }else {
                state = "";
            }
            if (GameData.getInstance().getState()==1) {
                if (GameData.getInstance().getNextMove().equals("")) {
                    state += "Make your move";
                } else {
                     state += "Moving to: " + GameData.getInstance().getNextMove();
                }
            } else if (GameData.getInstance().getState()==2) {
                state += "Next action: " + GameData.getInstance().getNextAction();
            }
        }

        gameStateLabel.setText(state);
        if (GameData.getInstance().getState() > 0) {
            gameButton.setEnabled(true);
        }
        if (GameData.getInstance().amIReady()) {
            if (!ready) {
                toggleReady();
            }
        } else {
            if (ready) {
                toggleReady();
            }
        }

        String serverversion = GameData.getInstance().getServersSuggestedClientVersion();
        if (serverversion.equals(SS13Client.CLIENT_VERSION_STRING)) {
            clientVersionLabel.setForeground(Color.GRAY);
            clientVersionLabel.setText("Your client is up to date (v. " + serverversion + ")");
        } else {
            clientVersionLabel.setForeground(Color.RED);
            clientVersionLabel.setText("Your client is out of date. Server requires v. " + serverversion);
        }

        roundNumberLabel.setText(GameData.getInstance().getRound() + "");
    }





}
