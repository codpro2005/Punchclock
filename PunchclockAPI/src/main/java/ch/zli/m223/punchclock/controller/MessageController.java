package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Message;
import ch.zli.m223.punchclock.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Gets all of the messages.
     * @returns all of the messages.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllEntries() {
        return messageService.findAll();
    }

    /**
     * Creates a new message.
     * @param message the message to be added.
     * @returns the added message.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@Valid @RequestBody Message message) {
        return messageService.createMessage(message);
    }

    /**
     * Deletes a specific message.
     * @param message the message to be deleted.
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMessage(@Valid @RequestBody Message message) { messageService.deleteMessage(message); }
}
