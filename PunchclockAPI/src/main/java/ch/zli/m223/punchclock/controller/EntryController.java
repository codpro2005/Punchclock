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

    /**
     * Returns all entries
     * @returns List of entries
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries() {
        return entryService.findAll();
    }

    /**
     * Adds the passed entry into the database
     * @param entry
     * @returns the added entry
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry) {
        return entryService.createEntry(entry);
    }

    /**
     * Adds a new entry with the checkIn value of the current time of calling the method and the creator id of the defined jwt into the database
     * @param request
     * @returns the added entry
     */
    @RequestMapping("/checkIn")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntryCheckIn(@Valid HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        User matchingUser = userController.getUserByJWT(request);
        return entryService.createEntryCheckIn(dateTime, matchingUser);
    }

    /**
     * Adds a checkOut value of the current time for the first entry found with no checkOut value for the user id of the defined jwt and saved it into the database.
     * @param request
     * @return
     */
    @RequestMapping("/checkOut")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry createEntryCheckOut(@Valid HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        User matchingUser = userController.getUserByJWT(request);
        return entryService.createEntryCheckOut(dateTime, matchingUser);
    }

    @RequestMapping("/checkIn/update")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntryCheckIn(@RequestBody Entry entry, HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.updateEntryCheckIn(matchingUser, entry);
    }

    @RequestMapping("/checkOut/update")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntryCheckOut(@RequestBody Entry entry, HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.updateEntryCheckOut(matchingUser, entry);
    }

    @RequestMapping("/current")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllCurrentUserEntries(@Valid HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.getAllCurrentUserEntries(matchingUser);
    }

    @RequestMapping("/current/delete")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllCurrentUserEntries(@Valid HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        entryService.deleteAllCurrentUserEntries(matchingUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@Valid @RequestBody Entry entry) { entryService.deleteEntry(entry); }
}
