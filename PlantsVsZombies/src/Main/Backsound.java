package Main;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Backsound {
    private Clip clip;

    public Backsound(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (audioFile.exists()) {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(audioFile));
            } else {
                System.err.println("File not found: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}