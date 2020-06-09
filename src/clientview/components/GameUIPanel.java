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
import java.util.Date;

public class GameUIPanel extends JPanel implements Observer {


    private static Timer timer;
    private static final int TIME_INTERVAL = 500;

    private final String username;
    private final SS13Client parent;
    private boolean stateUpdating;
    private int state = -1;
    private InGameView inGameView ;
    private boolean viewLobby = false;
    private LobbyView lobbyView;
    private SouthButtonPanel buttPanel;
    private int oldRound = -1;
    private long oldPollTime = 0;


    public GameUIPanel(String username, SS13Client parent) {
        this.parent = parent;
        this.username = username;
        this.setLayout(new BorderLayout());
        stateUpdating = false;
        inGameView = new InGameView(this);
        lobbyView = new LobbyView(username, parent);
        buttPanel = new SouthButtonPanel(this);
        this.add(buttPanel, BorderLayout.SOUTH);
        toggleView();
        GameData.getInstance().subscribe(this);
        GameUIPanel.pollServerSummary();
    }

    public void setUpPollingTimer(final String username) {
        pollServerWithList(username);
        if (timer != null && timer.isRunning()) {
            throw new IllegalStateException("Tried starting polling timer when it is already running!");
        } else {
            System.out.println("Started polling timer...");
            timer = new Timer(TIME_INTERVAL, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pollServerWithList(username);
                }
            });
            timer.start();
        }

    }

    private void pollServerWithList(String username) {
        if (System.currentTimeMillis() - 10000 > oldPollTime) {
            oldPollTime = System.currentTimeMillis();
            System.out.println("Client is still polling at " + new Date().toString());
        }
        ServerCommunicator.send(username + " LIST", new MyCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
                    timer.stop();
                    parent.switchBackToStart();
                } else {
                    GameData.getInstance().deconstructReadyListAndStateAndRoundAndSettings(result);
                }
            }

            @Override
            public void onFail() {
                System.out.println("Client: Failed to poll server, stopping timer!");
                timer.stop();
            }
        });
    }

    public JFrame getFrame() {
        return parent;
    }

    public String getUsername() {
        return username;
    }

    public void toggleView() {
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
        if (!stateUpdating) {
           if (newState == 0 || (newState >=1 && oldRound != GameData.getInstance().getRound())) {
                stateUpdating = true;
                oldRound = GameData.getInstance().getRound();

                if (state < 1 && GameData.getInstance().getRound() == 1) {
                    System.out.println("Looks like a new game now...");
                    if (viewLobby) {
                        System.out.println("Toggling away from lobby, into map");
                        toggleView();

                    }
                    GameData.getInstance().setSelectedRoom(0);
                    inGameView.getLastTurnPanel().clear();
                    buttPanel.toggleViewButton();
                }

                if (newState == 1 || newState == 2) {    // state is now movement
                    System.out.println("State is now movement/actions, asking for movement data");
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
                    System.out.println("Asking server for action data");
                    pollServerActions();
                    inGameView.getMapPanel().createRooms();
                    parent.stopPlayingBackgroundMusic();
                } else if (newState == 0) { // state is now pre-game
                    if (state == 2 || state == 1) { // went to pre-game from non-pregame state
                        pollServerActions();
                        pollServerSummary();
                        //MusicPlayer.playBackgroundMusic();
                        GameData.getInstance().clearActionList();
                        GameData.getInstance().setSelectedRoom(0);
                        inGameView.getMapPanel().createRooms();
                        lobbyView.flipToSummary();
                        oldRound = -1;
                        toggleView();
                        parent.playBackgroundMusic(true);
                    }

                }

               this.state = newState;
            }
            stateUpdating = false;
        }

    }


    public LobbyView getLobbyView() {
        return lobbyView;
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
                }
                GameData.getInstance().setSummaryString(result);

            }

            @Override
            public void onFail() {
                System.out.println("Failed to send SUMMARY message to server.");
            }
        });
    }

    private void pollServerMovement() {
        ServerCommunicator.send(GameData.getInstance().getClid() + " MOVEMENT", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
                }
                GameData.getInstance().deconstructMovementData(result);
            }

            @Override
            public void onFail() {
                System.out.println("Failed to send MOVEMENT message to server");
            }
        });
    }

    public static void pollServerActions() {
        System.out.println("Client: Polling actions!");
        ServerCommunicator.send(GameData.getInstance().getClid() + " ACTIONS", new MyCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result.contains("ERROR")) {
                    JOptionPane.showMessageDialog(null, result);
                }
                GameData.getInstance().clearRoomColors();
                GameData.getInstance().deconstructActionData(result);


            }

            @Override
            public void onFail() {
                System.out.println("Failed to send ACTIONS message to server");
            }
        });
    }

    public void toggleReady() {
        buttPanel.readyButtonPressed();
    }

    public InGameView getInGameView() {
        return inGameView;
    }


    public SS13Client getParentMain() {
        return parent;
    }

    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
        inGameView.unregisterYourSubscribers();
        buttPanel.unregisterYourself();
        lobbyView.unregisterYourSubscribers();
    }
}
