package com.matteoveroni.mydiary.note.model;

import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class NoteModel{

    private final DAO databaseManager = DAO.getInstance();

    public Note getFirstNote() {
        return (Note) databaseManager.read(Note.class, null, DAO.ElementsOnWhichOperate.FIRST);
    }

    public Note getLastNote() {
        return (Note) databaseManager.read(Note.class, null, DAO.ElementsOnWhichOperate.LAST);
    }

    public Note getNote(long noteId) {
        return (Note) databaseManager.read(Note.class, noteId, DAO.ElementsOnWhichOperate.REQUESTED);
    }

    public Note getPreviousNote(Note currentNote) {
        return (Note) databaseManager.read(Note.class, currentNote.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
    }

    public Note getNextNote(Note currentNote) {
        return (Note) databaseManager.read(Note.class, currentNote.getId(), DAO.ElementsOnWhichOperate.NEXT);
    }

    public void updateNote(Note noteToUpdate) {
        databaseManager.update(noteToUpdate);
    }

    public void saveNote(Note noteToSave) {
        databaseManager.write(noteToSave);
    }
}
