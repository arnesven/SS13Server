import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

import comm.ServiceHandler;

import graphics.pdf.MapPDFMaker;
import model.GameData;
import util.GameRecovery;
import util.Logger;


public class SS13ServerMain {


	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		int port = 55444;
		String name = "Winter Wonderland";
		ServerSocket listener = null;
		if (args.length == 2) {
			name = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		GameData gameData = new GameData();
		
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
