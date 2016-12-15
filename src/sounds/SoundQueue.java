package sounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/12/16.
 */
public class SoundQueue extends ArrayList<Sound> implements Serializable {

    private int index = -1;

    public int getCurrentIndex() {
        return index;
    }

    public List<String> getSoundsFrom(int i) {
        List<String> res = new ArrayList<>();
        for ( ; i < this.size(); ++i) {
            res.add(this.get(i).getSource());
        }
        return res;
    }

    @Override
    public boolean add(Sound s) {
        boolean res = super.add(s);
        index++;
        return res;
    }
}
