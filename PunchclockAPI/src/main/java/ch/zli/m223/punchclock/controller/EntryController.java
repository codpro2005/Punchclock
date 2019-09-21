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
     * Returns all entries.
     * @returns List of entries.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries() {
        return entryService.findAll();
    }

    /**
     * Adds the passed entry into the database.
     * @param entry the entry to add.
     * @returns the added entry.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry) {
        return entryService.createEntry(entry);
    }

    /**
     * Deletes the specific entry which has been passed.
     * @param entry the entry to be deleted.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@Valid @RequestBody Entry entry) { entryService.deleteEntry(entry); }

    /**
     * Adds a new entry with the checkIn value of the current time of calling the method and the creator id of the defined jwt into the database.
     * @param request the request to read the jwt and get the user out of it.
     * @returns the added entry.
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
     * Adds a checkOut value of the current time for the first entry found with no checkOut value for the user id of the defined jwt and saves it into the database.
     * @param request the request to read the jwt and get the user out of it.
     * @returns the updated entry.
     */
    @RequestMapping("/checkOut")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry createEntryCheckOut(@Valid HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        User matchingUser = userController.getUserByJWT(request);
        return entryService.createEntryCheckOut(dateTime, matchingUser);
    }

    /**
     * Updates the checkIn attribute of an Entry of a user and saves it into the database.
     * @param entry the new entry to be replaced with the old one.
     * @param request the request to read the jwt and get the user out of it.
     * @returns the updated entry.
     */
    @RequestMapping("/checkIn/update")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntryCheckIn(@RequestBody Entry entry, HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.updateEntryCheckIn(matchingUser, entry);
    }

    /**
     * Updates the checkOut attribute of an Entry of a user and saves it into the database.
     * @param entry the new entry to be replaced with the old one.
     * @param request the request to read the jwt and get the user out of it.
     * @returns the updated entry.
     */
    @RequestMapping("/checkOut/update")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntryCheckOut(@RequestBody Entry entry, HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.updateEntryCheckOut(matchingUser, entry);
    }

    /**
     * Returns all the entries of the user in the jwt.
     * @param request the request to read the jwt and get the user out of it.
     * @returns all of the entries from the user.
     */
    @RequestMapping("/current")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllCurrentUserEntries(@Valid HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        return entryService.getAllCurrentUserEntries(matchingUser);
    }

    /**
     * Deletes all of the entries created by the user in the jwt.
     * @param request the request to read the jwt and get the user out of it.
     */
    @RequestMapping("/current/delete")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllCurrentUserEntries(@Valid HttpServletRequest request) {
        User matchingUser = userController.getUserByJWT(request);
        entryService.deleteAllCurrentUserEntries(matchingUser);
    }
}
