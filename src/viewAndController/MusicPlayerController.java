package viewAndController;

import javax.swing.*;

public class MusicPlayerController {
    private JFrame parentFrame;

    public MusicPlayerController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JButton createMusicPlayerButton() {
        JButton openMusicPlayerButton = new JButton("Open Music Player");
        openMusicPlayerButton.addActionListener(e -> openMusicPlayer());
        return openMusicPlayerButton;
    }

    private void openMusicPlayer() {
        MusicPlayerDialog musicPlayerDialog = new MusicPlayerDialog(parentFrame);
        musicPlayerDialog.setLocationRelativeTo(parentFrame);
        musicPlayerDialog.setVisible(true);
    }
}
