package viewAndController;

import model.Note;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class NoteRenderer {
    private Image wholeNoteImage;
    private Image halfNoteImage;
    private Image quarterNoteImage;
    private Image eighthNoteImage;
    private Image sixteenthNoteImage;


    public NoteRenderer(int lineSpacing) {
        this.wholeNoteImage = loadAndScaleImage("whole-note-img.jpg", lineSpacing, Note.NoteType.WHOLE);
        this.halfNoteImage = loadAndScaleImage("half-note-img.jpg", lineSpacing, Note.NoteType.HALF);
        this.quarterNoteImage = loadAndScaleImage("quarter-note-img.jpg", lineSpacing, Note.NoteType.QUARTER);
        this.eighthNoteImage = loadAndScaleImage("eighth-note-img.jpg", lineSpacing, Note.NoteType.EIGHTH);
        this.sixteenthNoteImage = loadAndScaleImage("sixteenth-note-img.jpg", lineSpacing, Note.NoteType.SIXTEENTH);
    }



    private Image loadAndScaleImage(String fileName, int lineSpacing,Note.NoteType noteType) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream("/resources/images/" + fileName));
            int scaledWidth = (int)(lineSpacing * 0.8);
            int scaledHeight;
            switch (noteType) {
                case WHOLE:
                    scaledHeight = lineSpacing;
                    break;
                case HALF:
                case QUARTER:
                case EIGHTH:
                case SIXTEENTH:
                    scaledHeight = (int)(lineSpacing * 2);
                    break;
                default:
                    scaledHeight = lineSpacing;
            }
            return originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image: " + fileName);
            return null;
        }
    }

    public void renderNotes(Graphics g, List<Note> notes, int staffStartY, int lineSpacing) {
        for (Note note : notes) {
            Image noteImage = switch (note.getType()){
                case WHOLE -> wholeNoteImage;
                case HALF -> halfNoteImage;
                case QUARTER -> quarterNoteImage;
                case EIGHTH -> eighthNoteImage;
                case SIXTEENTH -> sixteenthNoteImage;
                default -> throw new IllegalArgumentException("Unsupported note type: " + note.getType());
            };
            int noteImageWidth = noteImage.getWidth(null);
            int noteImageHeight = noteImage.getHeight(null);
            int x = note.getPosition().x - noteImageWidth / 2;
            int y = note.getPosition().y - noteImageHeight / 2;

            int thirdLineY = staffStartY + 2 * lineSpacing;
            boolean flipHorizontally = note.getClickY() <= thirdLineY;

            if (flipHorizontally) {
                g.drawImage(noteImage,
                        x + noteImageWidth, y + noteImageHeight,
                        x, y,
                        0, 0,
                        noteImageWidth, noteImageHeight,
                        null);
            } else {
                // Draw the image normally
                g.drawImage(noteImage, x, y, null);
            }
        }
    }

}
