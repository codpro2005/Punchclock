package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Note;
import ch.zli.m223.punchclock.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }
}
