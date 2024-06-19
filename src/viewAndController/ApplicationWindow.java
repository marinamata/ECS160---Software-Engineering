package viewAndController;

import javax.swing.*;
import java.awt.*;

import model.Note;
import model.NoteManager;

/**
 * Main application window for the Music Notation Editor.
 * This class sets up the user interface and integrates various components like the staff panel and music player controller.
 */

public class ApplicationWindow extends JFrame {
    private StaffPanel staffPanel;
    private final NoteManager noteManager;
    private final MusicPlayerController musicPlayerController;


    /**
     * Constructs the main application window, initializing the note manager, staff panel, and music player controller.
     */

    public ApplicationWindow() {
        noteManager = new NoteManager();
        staffPanel = new StaffPanel(noteManager);
        musicPlayerController = new MusicPlayerController(this);
        initializeUI();
    }

    private void initializeUI() {
        configureWindow();
        addComponentsToPane();
    }

    /**
     * Configures the main window's properties like title, size, and default close operation.
     */

    private void configureWindow() {
        setTitle("Music Notation Editor");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
    }

    /**
     * Adds UI components to the content pane of the window.
     */
    private void addComponentsToPane() {
        getContentPane().add(InstructionsPanelFactory.createInstructionsPanel(), BorderLayout.EAST);
        getContentPane().add(createStaffPanel(), BorderLayout.CENTER);
        getContentPane().add(createNoteSelectionPanel(), BorderLayout.NORTH);
        getContentPane().add(musicPlayerController.createMusicPlayerButton(), BorderLayout.SOUTH);
    }

    private StaffPanel createStaffPanel() {
        this.staffPanel = new StaffPanel(noteManager);
        return staffPanel;
    }

    /**
     * Creates and returns a new instance of the NoteSelectionPanel, configuring its event listeners.
     * @return The created NoteSelectionPanel.
     */
    private NoteSelectionPanel createNoteSelectionPanel() {
        NoteSelectionPanel noteSelectionPanel = new NoteSelectionPanel(
                e -> noteTypeSelected(e.getActionCommand()),
                e -> clearStaff(),
                e -> playNotes()
        );
        return noteSelectionPanel;
    }

    /**
     * Handles the selection of a note type from the NoteSelectionPanel.
     * @param command The command string indicating the selected note type.
     */

    private void noteTypeSelected(String command) {
        Note.NoteType selectedNoteType = Note.NoteType.valueOf(command.toUpperCase());
        staffPanel.setCurrentNoteType(selectedNoteType);
    }


    private void playNotes() {
        for (Note note : noteManager.getNotes()) {
            int midiNoteNumber = note.getMidiNumber();
            int duration = calculateNoteDuration(note.getType());
            noteManager.getMidiPlayer().playNote(midiNoteNumber, 100, duration);
        }
    }

    private int calculateNoteDuration(Note.NoteType type) {
        return switch (type) {
            case WHOLE -> 2000;
            case HALF -> 1000;
            case QUARTER -> 500;
            case EIGHTH -> 250;
            case SIXTEENTH -> 125;
        };
    }
    private void clearStaff() {
        noteManager.clearNotes();
        staffPanel.repaint();
    }
}

