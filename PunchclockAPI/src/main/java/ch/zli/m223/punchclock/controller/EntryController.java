package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private UserController userController;

    public EntryController(EntryService entryService, UserController userController) {
        this.entryService = entryService;
        this.userController = userController;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries() {
        return entryService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry) {
        return entryService.createEntry(entry);
    }

    @RequestMapping("/checkIn")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntryCheckIn(@Valid HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        User matchingUser = userController.getUserByJWT(request);
        return entryService.createEntryCheckIn(dateTime, matchingUser);
    }

    @RequestMapping("/checkOut")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry createEntryCheckOut(@Valid HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        User matchingUser = userController.getUserByJWT(request);
        return entryService.createEntryCheckOut(dateTime, matchingUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@Valid @RequestBody Entry entry) { entryService.deleteEntry(entry); }
}
