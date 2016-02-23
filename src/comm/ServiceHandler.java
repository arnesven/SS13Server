package comm;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.GameData;


public class ServiceHandler {

	private GameData gameData;
	
	private List<MessageHandler> handlers = new ArrayList<>();

	public ServiceHandler(GameData gameData) {
		this.gameData = gameData;
		
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
		
	}

	public void serv(Socket socket) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		
		String message = (String)ois.readObject();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		for (MessageHandler handler : handlers) {
			if (handler.handle(message, oos)) {
				return;
			}
		}
		
		oos.writeObject("ERROR");
		throw new RuntimeException("Unknown message" + message);
	}


}
