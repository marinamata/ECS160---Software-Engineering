package viewAndController;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private Clip clip;
    private boolean looping = false;
    private int currentFramePosition = 0;

    public void loadAudio(File audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (clip != null) {
            clip.close();
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        if (looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
            if (looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            currentFramePosition = 0;
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            currentFramePosition = clip.getFramePosition();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(currentFramePosition);
            clip.start();
        }
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
        if (clip != null) {
            if (looping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.loop(0);
            }
        }
    }

    public boolean isLooping() {
        return this.looping;
    }

    public void reset() {
        if (clip != null) {
            clip.close();
        }
        looping = false;
        currentFramePosition = 0;
    }
}

