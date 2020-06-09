package clientsound;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundJLayer extends PlaybackListener implements Runnable
{
    private InputStream inputStream;
    private List<byte[]> additionalSounds;
    private AdvancedPlayer player;
    private Thread playerThread;
    private boolean doesRepeat = false;
    private boolean isPlaying;
    private static boolean soundIsOn = true;

    public SoundJLayer(String filePath, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
    }

    public SoundJLayer(byte[] byteData, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = new ByteArrayInputStream(byteData);
    }


    public SoundJLayer(String filePath) {
        this(filePath, false);
    }

    public SoundJLayer(byte[] byteData) {
        this(byteData, false);
    }

    public SoundJLayer(List<byte[]> byteList) {
        this(byteList.get(0), false);
        this.additionalSounds = byteList;
        byteList.remove(0);
    }


    public void play()
    {
        if (soundIsOn) {
            try {

                this.playerThread = new Thread(this, "AudioPlayerThread");

                this.playerThread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        if (isPlaying) {
            this.player.stop();
            try {
                doesRepeat = false;
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
 //       System.out.println("playbackStarted()");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
  //      System.out.println("playbackEnded()");
    }

    // Runnable members

    public void run()
    {
        isPlaying = true;
        boolean moreToPlay = false;
        do {
            try {
                this.player = new AdvancedPlayer
                        (
                                inputStream,
                                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
                        );

                this.player.setPlayBackListener(this);
                this.player.play();
            } catch (javazoom.jl.decoder.JavaLayerException ex) {
                ex.printStackTrace();
            }
            if (additionalSounds != null && additionalSounds.size() > 0) {
                moreToPlay = true;
                inputStream = new ByteArrayInputStream(additionalSounds.get(0));
                additionalSounds.remove(0);
                System.out.println("Had at least one more sound to play!");
            } else {
                moreToPlay = false;
            }
        } while (doesRepeat || moreToPlay);
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public static void setSoundEnabled(boolean b) {
        soundIsOn = b;
    }
}
