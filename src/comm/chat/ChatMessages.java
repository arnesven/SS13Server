package comm.chat;

import model.Player;
import util.HTMLText;
import util.Logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by erini02 on 26/11/16.
 */
public class ChatMessages implements Serializable {

//    private List<String> chatMessages = new ArrayList<>();
//    private int offset = 0;


    private Map<Player, List<String>> chatMessages = new HashMap<>();
    private Map<Player, Integer> offsets = new HashMap<>();


    private static final int BUFFER_MAX = 50;

    public ChatMessages() {
        this.serverSay("Server started");
    }

    private void add(String s, Player player, boolean withTimeStamp) {
        if (chatMessages.get(player) == null) {
            chatMessages.put(player, new ArrayList<>());
            offsets.put(player, 0);
        }


        Logger.log(Logger.INTERESTING, "[CHAT] " + s);
        if (withTimeStamp) {
            chatMessages.get(player).add(makeTimeStamp() + s);
        } else {
            chatMessages.get(player).add(s);
        }
        if (chatMessages.size() > BUFFER_MAX) {
            chatMessages.remove(0);
            offsets.put(player, offsets.get(player) + 1);
        }
    }

    public void add(String s, Player player) {
       this.add(s, player, true);
    }

    public void addAll(String s) {
        for (Player p : chatMessages.keySet()) {
            add(s, p);
        }
    }

    private String makeTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH.mm]");
        return sdf.format(new Date());
    }

    public void serverSay(String s) {
        this.addAll(HTMLText.makeText("green", s));
    }

    public void serverSay(String s, Player player) {
        this.add(HTMLText.makeText("green", s), player);
    }

    public int getLastMessageIndex(Player player) {
        if (chatMessages.get(player) == null) {
            chatMessages.put(player, new ArrayList<>());
            offsets.put(player, 0);
        }

        return offsets.get(player) + chatMessages.get(player).size() - 1;
    }

    public List<String> getMessagesFrom(int i, Player player) {
        if (chatMessages.get(player) == null) {
            chatMessages.put(player, new ArrayList<>());
            offsets.put(player, 0);
        }
        return chatMessages.get(player).subList(Math.max(0, i-offsets.get(player)), chatMessages.get(player).size());
    }

    public void plebOSSay(String s, boolean center, Player player) {
        if (s.length() < 80) {
            if (center) {
                s = padCenter(s);
            } else {
                s = pad(s);
            }
        }
        this.add(HTMLText.makeText("yellow", "black",
                "Courier", 4, s), player, false);
    }

    public void plebOSSay(String s, Player player) {
       plebOSSay( s , false, player);
    }

    private String padCenter(String s) {
        StringBuffer buf = new StringBuffer(s);
        boolean left = true;
        while (buf.length() < 60) {
            if (left) {
                buf.append('¨');
            } else {
                buf.insert(0, '¨');
            }
            left = !left;
        }
        return buf.toString();
    }

    private static String pad(String s) {
        StringBuffer buf = new StringBuffer(s);
        while (buf.length() < 60) {
            buf.append('¨');
        }
        return buf.toString();
    }
}
