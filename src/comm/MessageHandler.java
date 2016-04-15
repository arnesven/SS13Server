package comm;
import java.io.IOException;
import java.io.ObjectOutputStream;


public interface MessageHandler {
	
	boolean handle(String message, 
			ObjectOutputStream oos) throws IOException;

}
