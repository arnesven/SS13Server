package clientcomm;

import clientlogic.GameData;
import main.SS13Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommunicator {

    private static List<Long> requestTimes = new ArrayList<>();
    private static double average = 0.0;
    private static final long serialVersionUID = 8220637679515882442L;
    private static SS13Client client;

    public synchronized static void send(String message, MyCallback callback) {

        System.out.println("Sending \"" + message + "\"");
        long dt = System.currentTimeMillis();
        String res = login(message, GameData.getInstance().getHost(), GameData.getInstance().getPort());
	   //doStatistics(dt);

		if (!res.equals("fail")) {
            callback.onSuccess(res);
            dt = System.currentTimeMillis() - dt;
            System.out.println("Took " + dt + "ms to process result");
        } else {
		    callback.onFail();
        }
	}

    private static void doStatistics(long dt) {
        requestTimes.add(dt);
        if (requestTimes.size() > 100) {
            requestTimes.remove(0);
            long sum = 0;
            for (Long l : requestTimes) {
                sum += l;
            }
            average = sum / 100.0;
        }

//        if ((System.currentTimeMillis() / 1000) % 60 == 0) {
//            System.out.println("Average latency for server request: " + String.format("%1.1f", average) + " ms");
//        }
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
