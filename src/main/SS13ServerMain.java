package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

import comm.ServiceHandler;

import comm.chat.MugshotChatHandler;
import graphics.BackgroundSprites;
import graphics.UserInterface;
import model.GameData;
import util.GameRecovery;
import util.Logger;

/**
     * Main Class of SS13 server. This is where it all begins...
     * This class is mainly responsible for handeling arguments and creating
     * the instance of GameData.
 */

public class SS13ServerMain extends Thread {

    private final int port;
    private final String name;
    private final boolean dorecover;
    private final boolean removeOldData;

    public SS13ServerMain(int port, String name, boolean recover, boolean removeOldData) {
        this.port = port;
        this.name = name;
        this.dorecover = recover;
        this.removeOldData = removeOldData;
        Locale.setDefault(Locale.US);
    }


    @Override
    public void run() {
        ServerSocket listener = null;

        new UserInterface(); // needed for some sprites to be initialized
        new BackgroundSprites();
        MugshotChatHandler.loadMugshots();
        GameData gameData = new GameData(dorecover);

        if (this.removeOldData) {

            if (GameRecovery.removeDataFile()) {
                Logger.log(Logger.CRITICAL, "REMOVED OLD GAME DATA.");
            } else {

                Logger.log(Logger.CRITICAL, "Tried to remove old game data, but none was found.");
            }

        } else if (this.dorecover && !removeOldData) {
                try {
                    Logger.log(Logger.CRITICAL, "TRYING TO RECOVER DATA");
                    gameData = GameRecovery.recover();
                    Logger.log(Logger.CRITICAL, "GAME RECOVERED!");
                    gameData.getChat().serverSay("Server has been recovered from crash");
                    gameData.allClearReady();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException fnfe) {
                    Logger.log(Logger.CRITICAL, "Recovery file not found :-(");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        ServiceHandler serviceHandler = new ServiceHandler(name, gameData, port);

        try {
            listener = new ServerSocket(port);
            Logger.log("SS13 server \"" + name + "\" running on " + port + "...");

            do {
                Socket socket = listener.accept();
                serviceHandler.serv(socket);
                socket.close();
            } while (true);

        } catch (Exception e) {
            Logger.log(Logger.CRITICAL, e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                listener.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    /**
	 * @param args port name recover
	 */
	public static void main(String[] args) {
        if (args.length >= 3) {
            new SS13ServerMain(Integer.parseInt(args[1]), args[0], args[2].equals("recover"), false).run();
        } else {
            throw new IllegalArgumentException("Correct number of arguments are 3: [name] [port] recover");
        }



    }
}
