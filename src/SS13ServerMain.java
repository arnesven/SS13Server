import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import comm.ServiceHandler;

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

		try {
			listener = new ServerSocket(port);
			Logger.log("SS13 server \"" + name + "\" running on " + port  + "...");

			do {
				//System.out.println("Waiting for connection...");
				Socket socket = listener.accept();
				//System.out.println("someone connected...");

				serviceHandler.serv(socket);

				socket.close();

				//gameData.printClients();
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			listener.close();
		}
	}
	



}
