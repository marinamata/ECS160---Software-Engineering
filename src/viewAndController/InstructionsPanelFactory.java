package viewAndController;

import javax.swing.*;
import java.awt.*;

public class InstructionsPanelFactory {

    public static JPanel createInstructionsPanel() {
        JPanel instructionsPanel = new JPanel(new BorderLayout());

        String instructionsHtml = "<html><body style='width: 200px; padding: 5px;'>"
                + "<b>Instructions Staff:</b><br>"
                + "1. Click on the type of note you want to place.<br>"
                + "2. Click on the staff where you want the note to appear.<br>"
                + "3. If you wish to remove a note, simply click on it again.<br>"
                + "4. To play the notes on the staff, click the Play button.<br>"
                + "5. Remember, while playing, you cannot make any changes or stop playing. <br>"
                + "6. To clear all notes and start fresh, click the Clear All button.<br><br>"
                + "<b>Using the Music Player:</b><br>"
                + "1. Click Open Music Player to launch the audio player dialog.<br>"
                + "2. Use the Open File button to load an audio file.<br>"
                + "3. Use the Play/Restart button to start or restart playback, the Pause button to pause, "
                + "the Stop button to stop, and the Resume button to continue playback.<br>"
                + "4. The Loop button will toggle continuous looping of the audio. <br>"
                + "5. Closing the Music Player resets it.<br>"
                + "6. Note: The music player accepts only .wav files."
                + "</body></html>";

        JLabel instructionsLabel = new JLabel(instructionsHtml);
        instructionsLabel.setVerticalAlignment(SwingConstants.TOP);

        JScrollPane scrollPane = new JScrollPane(instructionsLabel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(250, 0)); // Set the preferred width for the instructions panel

        instructionsPanel.add(scrollPane, BorderLayout.CENTER);

        return instructionsPanel;
    }
}

