package viewAndController;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import model.Note;
import model.NoteManager;

/**
 * Represents the musical staff panel in the Music Notation Editor application.
 * This panel is responsible for displaying the musical staff, notes, and handling user interactions for note placement.
 */

public class StaffPanel extends JPanel {
    private final int lineSpacing = 30; // Adjust as needed
    private final int numberOfLines = 5; // Standard staff has 5 lines
    private final int strokeWidth = 2; // Thickness of the staff lines
    private final double horizontalMarginPercentage = 0.10; // 15% margin on each side

    private NoteManager noteManager;
    private NoteRenderer noteRenderer;
    private Note.NoteType currentNoteType = Note.NoteType.WHOLE;

    private Image trebleClefImage;


    public StaffPanel(NoteManager noteManager) {
        this.setBackground(Color.WHITE);
        this.noteManager = noteManager;
        this.noteRenderer = new NoteRenderer(lineSpacing);
        setupMouseListener();
        loadTrebleClefImage();
    }

    /**
     * Sets up the mouse listener for note placement and removal.
     */
    private void setupMouseListener() {
        addMouseListener(new StaffPanelMouseListener());
    }

    /**
     * Inner class to handle mouse clicks for placing or removing notes.
     */
    private class StaffPanelMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickPoint = e.getPoint();
            if (isClickWithinStaff(clickPoint) && !noteManager.removeNoteAtPoint(clickPoint)) {
                int midiNoteNumber = getMidiNoteNumberForYCoordinate(clickPoint.y);
                noteManager.addNote(clickPoint, currentNoteType, midiNoteNumber, e.getY()); // Pass click Y-coordinate
            }
            repaint();
        }
        /**
         * Determines if a given click point is within the staff area.
         * @param clickPoint The point where the mouse was clicked.
         * @return true if the click is within the staff, false otherwise.
         */
        private boolean isClickWithinStaff(Point clickPoint) {
            int staffStartY = calculateStartY();
            int staffEndY = staffStartY + (numberOfLines - 1) * lineSpacing;
            int staffStartX = (int) (getWidth() * horizontalMarginPercentage) + trebleClefImage.getWidth(null) * 2 / 3;
            int staffEndX = getWidth() - staffStartX;

            return clickPoint.y >= staffStartY && clickPoint.y <= staffEndY
                    && clickPoint.x >= staffStartX && clickPoint.x <= staffEndX;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStaff(g);

        int marginWidth = (int) (getWidth() * horizontalMarginPercentage);
        int staffStartY = calculateStartY(); // Calculate the Y-coordinate for the top line of the staff
        int clefX = marginWidth - trebleClefImage.getWidth(null) / 2;
        int clefY = staffStartY + (lineSpacing * 4 - trebleClefImage.getHeight(null)) / 2; // Position for the treble clef

        if (trebleClefImage != null) {
            g.drawImage(trebleClefImage, clefX, clefY, this);
        }

        noteRenderer.renderNotes(g, noteManager.getNotes(), staffStartY, lineSpacing);
    }


    private void drawStaff(Graphics g) {
        Graphics2D g2 = createGraphics2D(g);
        int startY = calculateStartY();
        drawLines(g2, startY);
    }

    /**
     * Creates and configures a Graphics2D object for drawing.
     * @param g The original Graphics object.
     * @return A configured Graphics2D object.
     */
    private Graphics2D createGraphics2D(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(strokeWidth));
        return g2;
    }
    private int calculateStartY() {
        int staffHeight = (numberOfLines - 1) * lineSpacing;
        return (getHeight() - staffHeight) / 2;
    }
    private void drawLines(Graphics2D g2, int startY) {
        int panelWidth = getWidth();
        int marginWidth = (int) (panelWidth * horizontalMarginPercentage);
        int lineStartX = marginWidth;
        int lineEndX = panelWidth - marginWidth;

        for (int i = 0; i < numberOfLines; i++) {
            int y = startY + (i * lineSpacing);
            g2.drawLine(lineStartX, y, lineEndX, y);
        }
    }
    public void setCurrentNoteType(Note.NoteType noteType) {
        this.currentNoteType = noteType;
    }


    public int getMidiNoteNumberForYCoordinate(int yCoordinate) {
        int yCoordinateE4 = calculateStartY() + (numberOfLines - 1) * lineSpacing;
        int semitoneStepsAway = (yCoordinateE4 - yCoordinate) * 2 / lineSpacing;
        int midiNoteNumber = 64 + semitoneStepsAway; // 64 is MIDI note number for E4
        return midiNoteNumber;
    }

    private void loadTrebleClefImage() {
        trebleClefImage = loadImage("/resources/images/treble-clef.jpg", lineSpacing * numberOfLines);
        if (trebleClefImage == null) {
            // Log error and/or inform the user. Could also set a default or placeholder image here.
            System.err.println("Failed to load treble clef image.");
        }
    }

    private Image loadImage(String imagePath, int targetHeight) {
        try {
            Image originalImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);
            int scaledWidth = (originalWidth * targetHeight) / originalHeight;
            return originalImage.getScaledInstance(scaledWidth, targetHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}