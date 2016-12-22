package comm.chat;

import util.HTMLText;
import util.Logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class ChatMessages implements Serializable {

    private List<String> chatMessages = new ArrayList<>();
    int offset = 0;
    private static final int BUFFER_MAX = 50;

    public ChatMessages() {
        this.serverSay("Server started");
    }

    public void add(String s) {
        Logger.log(Logger.INTERESTING, "[CHAT] " + s);
        chatMessages.add(makeTimeStamp() + s);
        if (chatMessages.size() > BUFFER_MAX) {
            chatMessages.remove(0);
            offset++;
        }
    }

    private String makeTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH.mm]");
        return sdf.format(new Date());
    }

    public void serverSay(String s) {
        this.add(HTMLText.makeText("green", s));
    }

    public int getLastMessageIndex() {
        return offset + chatMessages.size() - 1;
    }

    public List<String> getMessagesFrom(int i) {
        return chatMessages.subList(Math.max(0, i-offset), chatMessages.size());
    }
}
