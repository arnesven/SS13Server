package comm.chat;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
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
       addAll(s, true);
    }

    public void addAll(String s, boolean timeStamp) {
        for (Player p : chatMessages.keySet()) {
            add(s, p, timeStamp);
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

//    public void plebOSSay(String s, boolean center, Player player) {
//        if (s.length() < 80) {
//            if (center) {
//                s = padCenter(s);
//            } else {
//                s = pad(s);
//            }
//        }
//        //s = s.replace(" ", "");
//        this.add(HTMLText.makeText("yellow", "black",
//                "Courier", 4, s), player, false);
//    }
//
//    public void plebOSSay(String s, Player player) {
//        if (s.length() == 0) {
//            plebOSSay(s, false, player);
//            return;
//        }
//
//        while (s.length() > 80) {
//            StringBuffer buf = new StringBuffer(s.substring(0, 80));
//            plebOSSay(buf.toString(), false, player);
//            s = buf.toString();
//        }
//        if (s.length() > 0) {
//            plebOSSay(s, false, player);
//        }
//    }

    private String padCenter(String s) {
        StringBuffer buf = new StringBuffer(s);
        boolean left = true;
        while (buf.length() < 60) {
            if (left) {
                buf.append('▒');
            } else {
                buf.insert(0, '▒');
            }
            left = !left;
        }
        return buf.toString();
    }

    private static String pad(String s) {
        StringBuffer buf = new StringBuffer(s);
        while (buf.length() < 60) {
            buf.append('▒');
        }
        return buf.toString();
    }

    public void inCharacterSay(Player sender, String rest) {
        for (Actor a : sender.getPosition().getActors()) {
            if (a instanceof Player) {
                if (a == sender) {
                    this.add("You said " + "\"" + rest + "\"", (Player)a, false);
                } else {
                    this.add(sender.getCharacter().getPublicName() + " said " + "\"" + rest + "\"", (Player)a, false);
                }
            }
        }
    }

    public void overRadioSay(GameData gameData, Player sender, String whatWasSaid) {
        try {
            for (Room r : gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(sender.getPosition()).getName())) {
                for (Actor a : r.getActors()) {
                    if (a instanceof Player) {
                        if (a == sender) {
                            this.add(HTMLText.makeText("blue", "You said " + "\"" + whatWasSaid + "\""), (Player) a, false);
                        } else {
                            this.add(HTMLText.makeText("blue", sender.getCharacter().getRadioName() + " said " + "\"" + whatWasSaid + "\""), (Player) a, false);
                        }
                    }
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public void serverInSay(String sayWhat, Player receiver) {
        this.add(sayWhat, receiver);
    }

    public void serverInSay(String sayWhat) {
        this.addAll(sayWhat);
    }
}
