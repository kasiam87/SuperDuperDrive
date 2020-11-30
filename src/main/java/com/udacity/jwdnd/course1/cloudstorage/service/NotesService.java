package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean isNotePresent(Integer noteId){
        return getNote(noteId) != null;
    }

    public Note getNote(Integer noteId){
        return noteMapper.getNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId){
        return noteMapper.getNotes(userId);
    }

    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
