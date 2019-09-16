package ch.zli.m223.punchclock.repository;

import ch.zli.m223.punchclock.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
