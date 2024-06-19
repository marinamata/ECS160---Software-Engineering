package model;

import viewAndController.MidiPlayer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages the collection of musical notes and their interactions with the MIDI player.
 */

public class NoteManager {
    private List<Note> notes = new ArrayList<>();
    private MidiPlayer midiPlayer;
    private int noteWidth;
    private int noteHeight;

    public NoteManager() {
        this.midiPlayer = new MidiPlayer();
    }
    public MidiPlayer getMidiPlayer() {
        return midiPlayer;
    }

    /**
     * Adds a new note to the collection based on user input.
     *
     * @param position       The position where the note is placed.
     * @param type           The type of the note (e.g., whole, half, quarter).
     * @param midiNoteNumber The MIDI note number representing the note's pitch.
     * @param clickY         The Y-coordinate of the click, used for vertical placement.
     */

    public void addNote(Point position, Note.NoteType type, int midiNoteNumber, int clickY) {
        Note newNote = new Note(position, type, midiNoteNumber, clickY);
        notes.add(newNote);
    }

    public void clearNotes() {
        notes.clear();
    }

    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Attempts to remove a note located at a specific point.
     *
     * @param point The point where a user clicked, potentially indicating a note to remove.
     * @return true if a note was found and removed at the specified point; false otherwise.
     */

    public boolean removeNoteAtPoint(Point point) {
        final int tolerance = 10;

        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            Point notePosition = note.getPosition();

            // Calculate bounds considering tolerance for more user-friendly click detection

            int leftBound = notePosition.x - (noteWidth / 2) - tolerance;
            int rightBound = notePosition.x + (noteWidth / 2) + tolerance;
            int topBound = notePosition.y - (noteHeight / 2) - tolerance;
            int bottomBound = notePosition.y + (noteHeight / 2) + tolerance;

            if (point.x >= leftBound && point.x <= rightBound &&
                    point.y >= topBound && point.y <= bottomBound) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
