package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameUIPanel extends JPanel implements Observer {

    private final String username;
    private final JFrame parent;
    private boolean stateUpdating;
    private int state = -1;
    private InGameView inGameView ;
    private boolean viewLobby = false;
    private LobbyView lobbyView;
    private SouthButtonPanel buttPanel;
    private int oldRound = -1;


    public GameUIPanel(String username, JFrame parent) {
        this.setLayout(new BorderLayout());
        stateUpdating = false;
        this.username = username;
        inGameView = new InGameView(this);
        lobbyView = new LobbyView(username);
        this.parent = parent;
        buttPanel = new SouthButtonPanel(this);
        this.add(buttPanel, BorderLayout.SOUTH);

        //this.add(contentPane);
        toggleView();

        GameData.getInstance().subscribe(this);



    }

    public JFrame getFrame() {
        return parent;
    }

    public String getUsername() {
        return username;
    }

    public void toggleView() {
       // MyUtils.removeAllWidgets(this);
        if (viewLobby) {
            System.out.println("Showing In-game-clientview");
            this.remove(lobbyView);
            this.add(inGameView);
        } else {
            System.out.println("Showing lobby");
            this.remove(inGameView);
            this.add(lobbyView);
        }
        viewLobby = !viewLobby;
        revalidate();  // this messed up the key-listener on the frame?
        repaint();

    }


    @Override
    public void update() {
        int newState = GameData.getInstance().getState();
        // state has changed
        if (!stateUpdating) {
           if (newState == 0 || (newState >=1 && oldRound != GameData.getInstance().getRound())) {
              // System.out.println("Updating in GameUIPanel, old state: " + state + ", new state: " + newState);
                stateUpdating = true;
                oldRound = GameData.getInstance().getRound();

                if (state < 1 && GameData.getInstance().getRound() == 1) {
                    System.out.println("Looks like a new game now...");
//                    if (!inGameView.isInMapMode() && !iAmSpectator()) {
//                        toggleMapView();
//                    }
                    if (viewLobby) {
                        System.out.println("Toggling away from lobby, into map");
                        toggleView();

                    }
                    GameData.getInstance().setSelectedRoom(0);
                    inGameView.getLastTurnPanel().clear();
                    buttPanel.toggleViewButton();
                }


                //buttPanel.setUnready();
                if (newState == 1 || newState == 2) {    // state is now movement
                    System.out.println("State is now movement, asking for movement data");
                    pollServerMovement();
                    System.out.println("Old state is " + state);
                    if (viewLobby && !iAmSpectator()) {
                        System.out.println("not in map mode and not spectator! toggling map");
                        toggleView();
                    }
                    if (state == 2) {
                        System.out.println("Old state was actions, clearing action list");
                        GameData.getInstance().clearActionList();

                    }
                //} else if (newState == 2) { // state is now actions
                    pollServerActions();
                } else if (newState == 0) { // state is now pre-game
                    if (state == 2 || state == 1) { // went to pre-game from non-pregame state
                        pollServerActions();
                        pollServerSummary();
                        //MusicPlayer.playBackgroundMusic();
                        GameData.getInstance().clearActionList();
                        GameData.getInstance().setSelectedRoom(0);
                        lobbyView.flipToSummary();
                        oldRound = -1;
                        toggleView();
                    }

                }

               //System.out.println("Setting state to " + newState);
               this.state = newState;
            }
            stateUpdating = false;
        }

    }



    private boolean iAmSpectator() {
        return GameData.getInstance().amISpectator();
    }


    public static void pollServerSummary() {
        ServerCommunicator.send(GameData.getInstance().getClid() + " SUMMARY", new MyCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
//                    Window.alert(result);
//                    Window.Location.reload();
                }
                GameData.getInstance().setSummaryString(result);
            }
        });
    }

    private void pollServerMovement() {
        ServerCommunicator.send(GameData.getInstance().getClid() + " MOVEMENT", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
//                    Window.alert(result);
//                    Window.Location.reload();
                }
                GameData.getInstance().deconstructMovementData(result);
            }
        });
    }

    private void pollServerActions() {
        ServerCommunicator.send(GameData.getInstance().getClid() + " ACTIONS", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
//                    Window.alert(result);
//                    Window.Location.reload();
                    JOptionPane.showMessageDialog(null, result);
                }
                GameData.getInstance().deconstructActionData(result);
                if (inGameView.isInMapMode() && !iAmSpectator()) {
                    //System.out.println("Got action data, toggling map clientview");
                    //toggleMapView();
                }
                GameData.getInstance().clearRoomColors();
            }

        });
    }

    public void toggleReady() {
        buttPanel.readyButtonPressed();
    }

    public InGameView getInGameView() {
        return inGameView;
    }


//
//    protected void toggleMapView() {
//        inGameView.toggleMapView();
//
//    }



}
