package sounds;

import model.Player;
import model.PlayerSettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class SoundQueue extends ArrayList<Sound> implements Serializable {

    private final Player belong;
    private int index = -1;

    public SoundQueue(Player belongingTo) {
        this.belong = belongingTo;
    }

    public int getCurrentIndex() {
        return index;
    }

    public List<String> getSoundsFrom(int i) {
        List<String> res = new ArrayList<>();
        for ( ; i < this.size(); ++i) {
            res.add(this.get(i).getName());
        }
        return res;
    }

    @Override
    public boolean add(Sound s) {
        if (belong.getSettings().get(PlayerSettings.PLAY_SOUND_IF_SUPPORTED)) {
            boolean res = super.add(s);
            index++;
            return res;
        }
        return false;
    }

    @Override
    public void clear() {
        super.clear();
        index = -1;
    }
}
