package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Message;
import ch.zli.m223.punchclock.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
