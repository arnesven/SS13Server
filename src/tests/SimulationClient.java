package tests;

import util.MyArrays;
import util.MyRandom;
import util.MyStrings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

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
            System.out.println(clid + " is ready.");
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
                String movementData = sendToServer(clid + " MOVEMENT");
                handleMovementData(movementData);
            } else if (newState == 2) {
                String actionData = sendToServer(clid + " ACTIONS");
                handleActionData(actionData);
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

    private void handleActionData(String actionData) {
        //System.out.println(actionData);
        String[] parts = actionData.split("<player-data-part>");
        ActionTree at = ActionTree.parseTree(parts[0]);
        //at.print();
        List<String> randomAction = at.randomAction();
        if (at.getSubactions().size() > 1) {
            while (! isActionOK(randomAction)) {
                System.out.println(clid + " getting new random action, last was: " + randomAction.get(1));
                randomAction = at.randomAction();
            }
        }
        finalizeAction(randomAction);
    }

    private boolean isActionOK(List<String> randomAction) {
        if (randomAction.size() > 3) {
            if (randomAction.get(3).contains("Strip Naked") || randomAction.get(3).contains("All Items")) {
                return MyRandom.nextDouble() < 0.1;
            }
        }

        if (randomAction.size() > 2) {
            if (randomAction.get(2).contains("Drop")) {
                return MyRandom.nextDouble() < 0.333;
            }
        }


        return !randomAction.get(1).contains("Do Nothing");
    }

    private void finalizeAction(List<String> randomAction) {
        String toServer = MyStrings.join(randomAction, ",");
        toServer = toServer.substring(1, toServer.length() - 1);
        sendToServer(clid + " NEXTACTION " + toServer);
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
                Thread.currentThread().sleep(MyRandom.nextInt(1000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameState == 0 && MyRandom.nextDouble() < 0.1) {
                setReady(true);
            } else if (gameState != 0 && MyRandom.nextDouble() < 0.5) {
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
