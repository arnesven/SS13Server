package clientcomm;

import clientlogic.GameData;
import clientview.SS13Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerCommunicator {


    private static final long serialVersionUID = 8220637679515882442L;
    private static SS13Client client;

    public synchronized static void send(String message, MyCallback callback) {
		String res = login(message, GameData.getInstance().getHost(), GameData.getInstance().getPort());
		if (!res.equals("fail")) {
            callback.onSuccess(res);
        } else {
		    callback.onFail();
        }
	}

    //private final String serverFilePath;
    private static String login(String message, String host, int port) {
        return sendMess(message, host, port);

    }

    private static synchronized String sendMess(String message, String host, int port) {

        String response = "fail";
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            response = (String)(ois.readObject());

        } catch (IOException e) {
            System.err.println("Got IO exception " + response);
            //e.printStackTrace();
            client.showConnectionError();




            client.switchBackToStart();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println("Server: got message " + response);
        return response;
    }


    public static void setFrameReference(SS13Client ss13Client) {
        client = ss13Client;
    }
}
