package model;

import java.awt.Point;

public class Note {
    private Point position;
    private NoteType type;

    private int midiNumber;

    public enum NoteType {
        WHOLE, HALF, QUARTER, EIGHTH, SIXTEENTH
    }
    private int clickY;

    public Note(Point position, NoteType type, int midiNumber, int clickY) {
        this.position = position;
        this.type = type;
        this.midiNumber = midiNumber;
        this.clickY = clickY;
    }


    public Point getPosition() {
        return position;
    }

    public NoteType getType() {
        return type;
    }

    public int getMidiNumber() {
        return midiNumber;
    }
    public int getClickY() {
        return clickY;
    }

}
