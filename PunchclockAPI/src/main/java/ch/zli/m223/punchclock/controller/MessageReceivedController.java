package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.MessageReceived;
import ch.zli.m223.punchclock.service.MessageReceivedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// This Controller and the corresponding model only exist because there is no way to bind multiple OneToMany notations from one entity to another without it being of different type.
@RestController
@RequestMapping("/messagesReceived")
public class MessageReceivedController {
    private MessageReceivedService messageReceivedService;

    public MessageReceivedController(MessageReceivedService messageReceivedService) {
        this.messageReceivedService = messageReceivedService;
    }

    /**
     * Gets all of the received messages.
     * @returns all of the received messages.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageReceived> getAllEntries() {
        return messageReceivedService.findAll();
    }

    /**
     * Creates a new received message.
     * @param messageReceived the received message to be added.
     * @returns the added message received.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageReceived createMessageReceived(@Valid @RequestBody MessageReceived messageReceived) {
        return messageReceivedService.createMessageReceived(messageReceived);
    }

    /**
     * Deletes a specific message received.
     * @param messageReceived the received message to be deleted.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMessageReceived(@Valid @RequestBody MessageReceived messageReceived) { messageReceivedService.deleteMessageReceived(messageReceived); }
}
