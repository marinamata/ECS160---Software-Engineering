package viewAndController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MusicPlayerDialog extends JDialog {
    private final AudioPlayer audioPlayer;
    private File audioFile;

    public MusicPlayerDialog(Frame owner) {
        super(owner, "Music Player", true);
        this.audioPlayer = new AudioPlayer();
        setUpUI();
    }

    private void setUpUI() {
        setSize(300, 200); // Adjust size as needed
        setLayout(new FlowLayout());

        // Create UI components
        JButton openFileButton = createFileButton();
        JButton playButton = createPlayButton();
        JButton stopButton = createStopButton();
        JButton pauseButton = createPauseButton();
        JButton loopButton = createLoopButton();
        JButton resumeButton = createResumeButton();

        // Add components to the dialog
        add(openFileButton);
        add(playButton);
        add(stopButton);
        add(pauseButton);
        add(loopButton);
        add(resumeButton);

        // Add window closing behavior
        addWindowListener(new WindowClosingHandler());
    }

    private JButton createFileButton() {
        JButton button = new JButton("Open File");
        button.addActionListener(e -> openFile());
        return button;
    }

    private JButton createPlayButton() {
        JButton button = new JButton("Play/Restart");
        button.addActionListener(e -> playAudio());
        return button;
    }

    private JButton createStopButton() {
        JButton button = new JButton("Stop");
        button.addActionListener(e -> audioPlayer.stop());
        return button;
    }

    private JButton createPauseButton() {
        JButton button = new JButton("Pause");
        button.addActionListener(e -> audioPlayer.pause());
        return button;
    }

    private JButton createLoopButton() {
        JButton button = new JButton("Loop");
        button.addActionListener(e -> toggleLoop(button));
        return button;
    }

    private JButton createResumeButton() {
        JButton button = new JButton("Resume");
        button.addActionListener(e -> audioPlayer.resume());
        return button;
    }

    private void toggleLoop(JButton button) {
        boolean isCurrentlyLooping = audioPlayer.isLooping();
        audioPlayer.setLooping(!isCurrentlyLooping);
        button.setText(isCurrentlyLooping ? "Unloop" : "Loop");
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            audioFile = fileChooser.getSelectedFile();
            try {
                audioPlayer.loadAudio(audioFile);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading audio file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void playAudio() {
        if (audioFile != null) {
            audioPlayer.play();
        }
    }

    private class WindowClosingHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            audioPlayer.stop();
            audioPlayer.reset();
        }
    }
}
