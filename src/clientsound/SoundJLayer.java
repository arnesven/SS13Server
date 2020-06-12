package clientsound;

import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

public class SoundJLayer extends PlaybackListener implements Runnable
{
    private ClientSound currentSound;
    private String filePath;
    private InputStream inputStream;
    private List<ClientSound> additionalSounds;
    private AdvancedPlayer player;
    private Thread playerThread;
    private boolean doesRepeat = false;
    private boolean isPlaying;
    private static boolean soundIsOn = true;
    private AudioDevice device;

    public SoundJLayer(String filePath, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        this.filePath = filePath;
    }

    public SoundJLayer(ClientSound currentSound, boolean doesRepeat) {
        this.doesRepeat = doesRepeat;
        this.inputStream = new ByteArrayInputStream(currentSound.getBytes());
        this.currentSound = currentSound;
    }


    public SoundJLayer(String filePath) {
        this(filePath, false);
    }

    public SoundJLayer(ClientSound currentSound) {
        this(currentSound, false);
    }

    public SoundJLayer(List<ClientSound> byteList) {
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
            doesRepeat = false;
            this.player.stop();
            try {
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void run()
    {
        isPlaying = true;
        boolean moreToPlay = false;
        do {
            try {
                this.device = javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice();
                this.player = new AdvancedPlayer
                        (
                                inputStream,
                                device
                        );
                this.player.setPlayBackListener(this);
                adjustVolume();
                this.player.play();
            } catch (javazoom.jl.decoder.JavaLayerException ex) {
                ex.printStackTrace();
            }

            moreToPlay = hasAdditionalSoundsToPlay();
            if (doesRepeat) {
                refreshInputStream();
            }
        } while (doesRepeat || moreToPlay);
        isPlaying = false;
    }


    private void refreshInputStream() {
        System.out.println("This is a reapeating sound, playing again");
        if (filePath != null) {
            System.out.println("Repeating from file");
            inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        } else {
            System.out.println("Repeating from byte data");
            inputStream = new ByteArrayInputStream(currentSound.getBytes());
        }
    }

    private synchronized boolean hasAdditionalSoundsToPlay() {
        if (additionalSounds != null && additionalSounds.size() > 0) {
            inputStream = new ByteArrayInputStream(additionalSounds.get(0).getBytes());
            additionalSounds.remove(0);
            System.out.println("Had at least one more sound to play!");
            return true;
        }
        return false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public static void setSoundEnabled(boolean b) {
        soundIsOn = b;
    }

    public synchronized void addToQueue(List<ClientSound> byteList) {
        additionalSounds.addAll(byteList);
    }


    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent) { }

    public void playbackFinished(PlaybackEvent playbackEvent) { }


    private void adjustVolume() {
        if (currentSound != null && currentSound.getVolume() != 0.0f) {
            new Thread(new VolumeChanger(this), "volumechanger").start();
        }
    }

    private void setVolume(float gain) throws VolumeChangerException {
        Class<JavaSoundAudioDevice> clazz = JavaSoundAudioDevice.class;
        Field[] fields = clazz.getDeclaredFields();

        try {
            SourceDataLine source = null;
            for (Field field : fields) {
                if ("source".equals(field.getName())) {
                    field.setAccessible(true);
                    source = (SourceDataLine) field.get(device);
                    field.setAccessible(false);
                    if (source == null) {
                        throw new VolumeChangerException();
                    }
                    FloatControl volControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                    if (volControl != null) {
                //        System.out.println("Max volume is " + volControl.getMaximum());
                //        System.out.println("Min volume is " + volControl.getMinimum());
                        float newGain = Math.min(Math.max(gain, volControl.getMinimum()), volControl.getMaximum());
                        volControl.setValue(newGain);
                //        System.out.println("Set new gain to: " + newGain);
                    }
                }
            }
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
        }
    }


    private class VolumeChanger implements Runnable {
        private final SoundJLayer soundJLayer;

        public VolumeChanger(SoundJLayer soundJLayer) {
            this.soundJLayer = soundJLayer;
        }

        @Override
        public void run() {
            boolean volumeSet = false;
            do {
                try {
                    soundJLayer.setVolume(currentSound.getVolume());
                    volumeSet = true;
                } catch (Exception e) {

                }
            } while (!volumeSet);
            System.out.println("Volume adjuster Finishing");

        }
    }

    private class VolumeChangerException extends Exception {
    }


}
