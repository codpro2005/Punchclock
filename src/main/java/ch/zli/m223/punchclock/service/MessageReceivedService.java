package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.MessageReceived;
import ch.zli.m223.punchclock.repository.MessageReceivedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageReceivedService {
    private MessageReceivedRepository messageReceivedRepository;

    public MessageReceivedService(MessageReceivedRepository messageReceivedRepository) {
        this.messageReceivedRepository = messageReceivedRepository;
    }

    public MessageReceived createMessageReceived(MessageReceived messageReceived) {
        return messageReceivedRepository.saveAndFlush(messageReceived);
    }

    public void deleteMessageReceived(MessageReceived messageReceived) {
        messageReceivedRepository.delete(messageReceived);
    }

    public List<MessageReceived> findAll() {
        return messageReceivedRepository.findAll();
    }
}
