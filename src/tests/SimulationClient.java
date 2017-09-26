package tests;

import util.MyArrays;
import util.MyRandom;
import util.MyStrings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

/**
 * Created by erini02 on 26/09/17.
 */
public class SimulationClient extends Thread {
    private final int port;
    private final String clid;
    private String mapdata;
    private int gameState;
    private int currentRound;
    private String gameStateName;
    private String movementData;
    private String actionData;

    public SimulationClient(int port, String clientName) {
        this.port = port;
        this.clid = clientName;

        pingServer();
        System.out.println(sendToServer("IDENT ME" + clid));
        getMapData();
        pollServer();

        //System.out.println(sendToServer("SIM1 READY YES"));
        //System.out.println(sendToServer("SIM1 LIST"));


        start();
    }

    public String setReady(boolean ready) {
        if (ready) {
            return sendToServer(clid + " READY YES");
        }
        return sendToServer(clid + " READY NO");
    }

    public void pollServer() {
        String latestData = sendToServer(clid + " LIST");
        String[] parts = latestData.split("<player-data-part>");
        int newState = Integer.parseInt(parts[1]);
        if (newState != gameState) {
            System.out.println(clid + " Change in game state detected, going from " + getGameStateName() + " to " + SimulationClient.gameStateName(newState));
            if (newState == 1) {
                movementData = sendToServer(clid + " MOVEMENT");
                handleMovementData(movementData);
                //System.out.println("   MOVEMENT data: " + );
            } else if (newState == 2) {
                actionData = sendToServer(clid + " ACTIONS");
                //System.out.println("   ACTIONS data: " + );
            }

        }
        gameState = newState;

        currentRound = Integer.parseInt(parts[2]);


    }

    private void handleMovementData(String movementData) {
        String[] parts = movementData.split("<player-data-part>");
        Collection<Integer> possibleDestination = MyArrays.parseIntArray(parts[0]);
        int selectedMove = MyRandom.sample(possibleDestination);
        sendToServer(clid + " NEXTMOVE " + selectedMove);
    }


    private boolean pingServer() {
        String s = sendToServer("ALIVE");
        if (s == null) {
            return false;
        }
        System.out.println(s);
        return true;
    }

    private String sendToServer(String message) {
        Socket s = null;
        try {
            s = new Socket("localhost", port);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(message);
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                return (String)ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {

            pollServer();



            //System.out.println(clid + " polled server: State: " + getGameStateName() + ", round: " + currentRound);

            getMapData();

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameState == 0 && MyRandom.nextDouble() < 0.1) {
                setReady(true);
            } else if (gameState != 0 && MyRandom.nextDouble() < 0.7) {
                setReady(true);
            }
        }
    }

    public void getMapData() {
        mapdata = sendToServer(clid + " MAP 1000 1000");;
    }

    public String getGameStateName() {
        return gameStateName(gameState);
    }

    private static String gameStateName(int newState) {
        String state = "pre-game";
        if (newState == 1) {
            state = "Movement";
        } else if (newState == 2) {
            state = "Action";
        }
        return state;
    }
}
