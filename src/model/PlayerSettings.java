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

    private HashMap<String, Setting> settings = new HashMap<>();

    public PlayerSettings() {
        settings.put(AUTO_LOOT_ON_KILL, new Setting(false, false));
        settings.put(SHOW_ITEMS_IN_MAP_WHEN_DEAD, new Setting(true, true));
        settings.put(AUTO_DROP_ITEMS_ON_PICK_UP, new Setting(false, false));
        settings.put(ALWAYS_REFUSE_GIFTS, new Setting(false, false));
        settings.put(CURRENT_ROOM_STUFF_IN_MAP, new Setting(true, true));
        settings.put(MAKE_ME_AI_IF_ABLE, new Setting(false, false));
        settings.put(GIVE_ME_A_TASK, new Setting(true, false));
        settings.put(PLAY_SOUND_IF_SUPPORTED, new Setting(true, true));
        settings.put(MAKE_ME_A_SPECTATOR, new Setting(false, true));
        settings.put(ACTIVATE_MOVEMENT_POWERS, new Setting(false, true));
        settings.put(STYLE_BUTTONS_ON, new Setting(false, true));
        settings.put(NOT_BEING_A_WOMAN, new Setting(MyRandom.nextBoolean(), false));
    }

    public void set(String key, boolean value) {
        settings.get(key).setOn(value);
    }

    public boolean get(String key) {
        return settings.get(key).isOn();
    }

    public String makeIntoString() {
        List<String> strs = new ArrayList<>();
        for (Map.Entry<String, Setting> entry : settings.entrySet()) {
            if (!entry.getValue().isHidden()) {
                strs.add(entry.getKey() + "," + entry.getValue().isOn());
            }
        }
        strs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return MyStrings.join(strs);
    }

    private static class Setting {
        private boolean on;
        private boolean hidden;

        public Setting(boolean on, boolean hidden) {
            this.on = on;
            this.hidden = hidden;
        }

        public void setOn(boolean value) {
            on = value;
        }

        public boolean isOn() {
            return on;
        }

        public boolean isHidden() {
            return hidden;
        }
    }
}
