package model;

import util.MyStrings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 13/10/16.
 */
public class PlayerSettings implements Serializable {

    public static final String AUTO_LOOT_ON_KILL           = "Auto-loot on kill";
    public static final String SHOW_ITEMS_IN_MAP_WHEN_DEAD = "Show items in map when dead";
    public static final String AUTO_DROP_ITEMS_ON_PICK_UP  = "Auto-drop items on pick-up";

    private HashMap<String, Boolean> settings = new HashMap<>();

    public PlayerSettings() {
        settings.put(AUTO_LOOT_ON_KILL, false);
        settings.put(SHOW_ITEMS_IN_MAP_WHEN_DEAD, true);
        settings.put(AUTO_DROP_ITEMS_ON_PICK_UP, false);
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
        return MyStrings.join(strs, "|");
    }

}
