package model;

import util.MyRandom;
import util.MyStrings;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
public class PlayerSettings implements Serializable {

    public static final String AUTO_LOOT_ON_KILL           = "Auto-loot on kill";
    public static final String SHOW_ITEMS_IN_MAP_WHEN_DEAD = "Show items in map when dead";
    public static final String AUTO_DROP_ITEMS_ON_PICK_UP  = "Auto-drop items on pick-up";
    public static final String ALWAYS_REFUSE_GIFTS         = "Always refuse gifts";
    public static final String CURRENT_ROOM_STUFF_IN_MAP   = "Current room stuff in map";
    public static final String MAKE_ME_AI_IF_ABLE          = "Make me the AI if able";
    public static final String GIVE_ME_A_TASK              = "Give me a personal goal";
    public static final String PLAY_SOUND_IF_SUPPORTED     = "Play sound if supported";
    public static final String MAKE_ME_A_SPECTATOR         = "Make me a spectator";
    public static final String AUTO_READY_ME_IN_60_SECONDS = "Auto-ready me after 60 secs";
    public static final String ACTIVATE_MOVEMENT_POWERS    = "Activate Movement Powers";
    public static final String STYLE_BUTTONS_ON            = "Style Buttons On";
    public static final String NOT_BEING_A_WOMAN           = "Not being a anti-man";

    private HashMap<String, Boolean> settings = new HashMap<>();

    public PlayerSettings() {
        settings.put(AUTO_LOOT_ON_KILL, false);
        settings.put(SHOW_ITEMS_IN_MAP_WHEN_DEAD, true);
        settings.put(AUTO_DROP_ITEMS_ON_PICK_UP, false);
        settings.put(ALWAYS_REFUSE_GIFTS, false);
        settings.put(CURRENT_ROOM_STUFF_IN_MAP, true);
        settings.put(MAKE_ME_AI_IF_ABLE, false);
        settings.put(GIVE_ME_A_TASK, true);
        settings.put(PLAY_SOUND_IF_SUPPORTED, true);
        settings.put(MAKE_ME_A_SPECTATOR, false);
        settings.put(ACTIVATE_MOVEMENT_POWERS, true);
        //settings.put(AUTO_READY_ME_IN_60_SECONDS, true);
        settings.put(STYLE_BUTTONS_ON, false);
        settings.put(NOT_BEING_A_WOMAN, MyRandom.nextBoolean());
    }

    public void set(String key, boolean value) {
        settings.put(key, value);
    }

    public boolean get(String key) {
        return settings.get(key);
    }

    public String makeIntoString() {
        List<String> strs = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : settings.entrySet()) {
            strs.add(entry.getKey() + "," + entry.getValue());
        }
        strs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return MyStrings.join(strs);
    }
}
