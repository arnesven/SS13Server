package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

import comm.ServiceHandler;

import graphics.pdf.MapPDFMaker;
import model.GameData;
import util.GameRecovery;
import util.Logger;

/**
     * Main Class of SS13 server. This is where it all begins...
     * This class is mainly responsible for handeling arguments and creating
     * the instance of GameData.
 */

public class SS13ServerMain {


	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		int port = 55444;
		String name = "Donut SS13";
		ServerSocket listener = null;
		if (args.length >= 2) { // wi fixar
			name = args[0];
			port = Integer.parseInt(args[1]);
		}


        boolean dorecover = false;
        if (args.length > 2) {
            dorecover = args[2].equals("recover");
        }

		GameData gameData = new GameData(dorecover);
		
	  if (args.length == 3) {
            if (args[2].equals("pdf")) {
                MapPDFMaker.generate(gameData);
            } else if (args[2].equals("recover")) {
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
                }
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
                listener.close();
            }

	}
}
