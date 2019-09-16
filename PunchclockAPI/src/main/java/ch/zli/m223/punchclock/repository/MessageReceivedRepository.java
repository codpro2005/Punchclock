package ch.zli.m223.punchclock.repository;

import ch.zli.m223.punchclock.domain.MessageReceived;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReceivedRepository extends JpaRepository<MessageReceived, Long> {
}
