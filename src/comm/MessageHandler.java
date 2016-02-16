package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public interface MessageHandler {
	
	boolean handle(String message, 
			ObjectOutputStream oos) throws IOException;

}
