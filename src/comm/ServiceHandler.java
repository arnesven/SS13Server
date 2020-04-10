package comm;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.GameData;


public class ServiceHandler {

//	private GameData gameData;
	
	private List<MessageHandler> handlers = new ArrayList<>();
	private String name;

	public ServiceHandler(String name, GameData gameData, int port) {
		this.name = name;
//		this.gameData = gameData;

		handlers.add(new AliveHandler(gameData, name, port));
		handlers.add(new IDMessageHandler(gameData));
		
		handlers.add(new ReadyCommandHandler(gameData));
		
		handlers.add(new ListCommandHandler(gameData));
		
		handlers.add(new MapCommandHandler(gameData));
		
		handlers.add(new ReturningCommandHandler(gameData));

		handlers.add(new KickCommandHandler(gameData));
		
		handlers.add(new MovementCommandHandler(gameData));
		
		handlers.add(new NextMoveCommandHandler(gameData));
		
		handlers.add(new ActionCommandHandler(gameData));
		
		handlers.add(new NextActionCommandHandler(gameData));
		
		handlers.add(new SummaryCommandHandler(gameData));
		
		handlers.add(new JobsCommandHandler(gameData));
		
		handlers.add(new SettingsCommandHandler(gameData));

		handlers.add(new ResourceCommandHandler(gameData));

        handlers.add(new ChatHandler(gameData));

        handlers.add(new SoundHandler(gameData));

        handlers.add(new ServerInfoCommandHandler(gameData, new ServerInfoFactory(name, port)));

        handlers.add(new OverlayActionCommandHandler(gameData));

        handlers.add(new InventoryActionCommandHandler(gameData));

        handlers.add(new StyleCommandHandler(gameData));

        handlers.add(new FancyFrameCommandHandler(gameData));
		
	}

	public void serv(Socket socket) throws IOException, ClassNotFoundException, UnknownMessageException {
	    try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String message = (String) ois.readObject();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            for (MessageHandler handler : handlers) {
                if (handler.handle(message, oos)) {
                    return;
                }
            }

            oos.writeObject("ERROR: An unknown error has occurred on the server. The server has crashed - you crashed it.");
            throw new UnknownMessageException("Unknown message \"" + message + "\"");
        } catch (StreamCorruptedException sce) {
	        System.err.println(sce.getMessage());
        }
	}


}
