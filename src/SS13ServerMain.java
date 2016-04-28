import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import comm.ServiceHandler;

import graphics.pdf.MapPDFMaker;
import model.GameData;
import util.Logger;


public class SS13ServerMain {


	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int port = 55444;
		String name = "SS13 Server";
		ServerSocket listener = null;
		if (args.length == 2) {
			name = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		GameData gameData = new GameData();
		
		ServiceHandler serviceHandler = new ServiceHandler(name, gameData, port);
        if (args.length == 3 && args[2].equals("pdf")) {
            MapPDFMaker.generate(gameData);
        }

        try {
			listener = new ServerSocket(port);
			Logger.log("SS13 server \"" + name + "\" running on " + port  + "...");

			do {
				Socket socket = listener.accept();

				serviceHandler.serv(socket);

				socket.close();
    		} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			listener.close();
		}
	}
	



}
