package viewAndController;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
public class NoteSelectionPanel extends JPanel{
    private JButton wholeNoteButton;
    private JButton halfNoteButton;
    private JButton quarterNoteButton;
    private JButton eighthNoteButton;
    private JButton sixteenthNoteButton;
    private JButton clearAllButton;
    private JButton playButton;

    public NoteSelectionPanel(ActionListener noteSelectionListener, ActionListener clearAllListener, ActionListener playListener) {
        setLayout(new FlowLayout()); // Arrange buttons in a row

        // Initialize buttons
        wholeNoteButton = new JButton("Whole Note");
        halfNoteButton = new JButton("Half Note");
        quarterNoteButton = new JButton("Quarter Note");
        eighthNoteButton = new JButton("Eighth Note");
        sixteenthNoteButton = new JButton("Sixteenth Note");
        clearAllButton = new JButton("Clear All");
        playButton = new JButton("Play");

        // Add action listeners to buttons, passing the action to the provided noteSelectionListener
        wholeNoteButton.addActionListener(noteSelectionListener);
        halfNoteButton.addActionListener(noteSelectionListener);
        quarterNoteButton.addActionListener(noteSelectionListener);
        eighthNoteButton.addActionListener(noteSelectionListener);
        sixteenthNoteButton.addActionListener(noteSelectionListener);
        clearAllButton.addActionListener(clearAllListener);
        playButton.addActionListener(playListener);

        // Set action commands to help identify which button was pressed
        wholeNoteButton.setActionCommand("WHOLE");
        halfNoteButton.setActionCommand("HALF");
        quarterNoteButton.setActionCommand("QUARTER");
        eighthNoteButton.setActionCommand("EIGHTH");
        sixteenthNoteButton.setActionCommand("SIXTEENTH");

        // Add buttons to the panel
        add(wholeNoteButton);
        add(halfNoteButton);
        add(quarterNoteButton);
        add(eighthNoteButton);
        add(sixteenthNoteButton);
        add(clearAllButton);
        add(playButton);
    }
}
