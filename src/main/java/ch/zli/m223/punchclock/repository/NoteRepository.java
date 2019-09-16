package ch.zli.m223.punchclock.repository;

import ch.zli.m223.punchclock.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
