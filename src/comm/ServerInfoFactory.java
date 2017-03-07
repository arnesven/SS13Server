package comm;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by erini02 on 23/12/16.
 */
public class ServerInfoFactory {
    private final String name;
    private final int port;
    private String machineName;

    public ServerInfoFactory(String name, int port) {
        this.name = name;
        this.port = port;
        this.machineName = "Unknown Machine!";
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
            this.machineName = inet.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public String getInfoHTML() {
        return "<h1>Welcome to SS13</h1>" +
                "<center><img src=\"http://www.byond.com/games/hubicon/8266.png\"></center>"+
                "<h2>'" + name + "' running on " + machineName +":"+port + "</h2>"+
                "<b>What the hell is this?</b>" + "" +
                "<p>Check out the <a target=\"_blank\" href=\"https://gitlab.ida.liu.se/erini02/ss13/wikis/home\">wiki</a>.</p>";
    }
}
