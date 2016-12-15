package sounds;

import java.io.Serializable;

/**
 * Created by erini02 on 15/12/16.
 */
public class Sound implements Serializable {

    public static final Sound YOU_JUST_JOINED = new Sound("http://www.freesfx.co.uk/rx2/mp3s/3/15215_1460389654.mp3");
    public static final Sound NEW_GAME = new Sound("http://www.freesfx.co.uk/rx2/mp3s/3/15616_1460475412.mp3");
    public static final Sound EXPLOSION = new Sound("http://www.freesfx.co.uk/rx2/mp3s/2/14558_1460040286.mp3");
    private final String source;

    public Sound(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
