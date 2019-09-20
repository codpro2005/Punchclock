package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        Entry currentEntry = entryRepository.findAll().stream().filter(t -> t.getCreator().getId() == creator.getId()).findFirst().get();
        currentEntry.setCheckOut(dateTime);
        return entryRepository.save(currentEntry);
    }

    public void deleteEntry(Entry entry) {
        entryRepository.delete(entry);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
