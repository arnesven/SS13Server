package sounds;

import java.io.Serializable;

/**
 * Created by erini02 on 15/12/16.
 */
public class Sound implements Serializable {

    private final String source;
    private final String name;
    private float volume;


    public Sound(String source, float v) {
        this.source = source;
        this.volume = v;
        this.name = source + "" + volume;
        ServerSoundManager.register(this);

    }

    public Sound(String source) {
        this(source, 0.0f);
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public Sound newWithAdjustedVolume(float v) {
        Sound s = new Sound(source, v);
        return s;
    }
}
