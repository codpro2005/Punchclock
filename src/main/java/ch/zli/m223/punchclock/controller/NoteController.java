package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Note;
import ch.zli.m223.punchclock.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllEntries() {
        return noteService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@Valid @RequestBody Note note) {
        return noteService.createNote(note);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@Valid @RequestBody Note note) { noteService.deleteNote(note); }
}
