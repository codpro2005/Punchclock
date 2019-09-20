package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(Entry entry) {
        return entryRepository.saveAndFlush(entry);
    }

    public Entry createEntryCheckIn(LocalDateTime dateTime, User creator) {
        Entry newEntry = new Entry();
        User newUser = new User();
        newUser.setId(creator.getId());
        newEntry.setCreator(newUser);
        newEntry.setCheckIn(dateTime);
        return entryRepository.saveAndFlush(newEntry);
    }

    public Entry createEntryCheckOut(LocalDateTime dateTime, User creator) {
        Entry currentEntry = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == creator.getId() && t.getCheckOut() == null).findFirst().get();
        currentEntry.setCheckOut(dateTime);
        return entryRepository.save(currentEntry);
    }

    public Entry updateEntryCheckIn(User user, Entry newEntry) {
        List<Entry> currentUserEntries = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == user.getId()).collect(Collectors.toList());
        Entry currentEntry = currentUserEntries.get((int)newEntry.getId() - 1);
        currentEntry.setCheckIn(newEntry.getCheckIn());
        return entryRepository.save(currentEntry);
    }

    public Entry updateEntryCheckOut(User user, Entry newEntry) {
        List<Entry> currentUserEntries = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == user.getId()).collect(Collectors.toList());
        Entry currentEntry = currentUserEntries.get((int)newEntry.getId() - 1);
        currentEntry.setCheckOut(newEntry.getCheckOut());
        return entryRepository.save(currentEntry);
    }

    public List<Entry> getAllCurrentUserEntries(User user) {
        List<Entry> currentEntries = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == user.getId()).collect(Collectors.toList());
        return currentEntries;
    }

    public void deleteAllCurrentUserEntries(User user) {
        List<Entry> currentEntries = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == user.getId()).collect(Collectors.toList());
        entryRepository.deleteAll(currentEntries);
    }

    public void deleteEntry(Entry entry) {
        entryRepository.delete(entry);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
