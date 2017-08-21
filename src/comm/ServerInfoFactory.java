package comm;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by erini02 on 23/12/16.
 */
public class ServerInfoFactory {
    private static final int MAX_RSS_ITEMS = 5;
    private final String name;
    private final int port;
    private String rssFeedString = null;

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



        SyndFeedInput input = new SyndFeedInput();
        URL feedUrl;
        SyndFeed feed = null;
        try {
            feedUrl = new URL("https://gitlab.ida.liu.se/erini02/ss13/commits/master.atom");
            feed = input.build(new XmlReader(feedUrl));
            rssFeedString = makeNiceFeed(feed);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String makeNiceFeed(SyndFeed feed) {
        List<SyndEntry> entries =  feed.getEntries();
        StringBuffer buf = new StringBuffer("<b>Latest Updates:</b><br/><p><ul>");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM");

        int num = 1;
        for (SyndEntry e : entries) {

            buf.append("<li>" +  e.getTitle()
                    + " <span style='color:gray;font-size:10pt'>(" + e.getUpdatedDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime().format(dtf)
                    + ")</span>"
                    + "</li>");

            if (num == MAX_RSS_ITEMS) {
                break;
            }
            num++;
        }

        buf.append("</p></ul>");
        return buf.toString();

    }

    public String getInfoHTML() {
        return "<h1>Welcome to SS13</h1>" +
                "<center><img src=\"http://www.byond.com/games/hubicon/8266.png\"></center>"+
                "<h2>'" + name + "' running on " + machineName +":"+port + "</h2>"+
                "<b>What the hell is this?</b>" + "" +
                "<p>Check out the <a target=\"_blank\" href=\"https://gitlab.ida.liu.se/erini02/ss13/wikis/home\">wiki</a>.</p>"+
                rssFeedString;
    }
}
