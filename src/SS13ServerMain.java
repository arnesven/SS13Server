import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Random;

import comm.ServiceHandler;

import model.GameData;


public class SS13ServerMain {

	private static final int port = 55444;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket listener = null;
		GameData gameData = new GameData();
		ServiceHandler serviceHandler = new ServiceHandler(gameData);
		do {
			try {
				listener = new ServerSocket(port);
				System.out.println("SS13 server running...");

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
		} while (true);

	}
	



}
