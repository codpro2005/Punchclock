package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.MessageReceived;
import ch.zli.m223.punchclock.service.MessageReceivedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messagesReceived")
public class MessageReceivedController {
    private MessageReceivedService messageReceivedService;

    public MessageReceivedController(MessageReceivedService messageReceivedService) {
        this.messageReceivedService = messageReceivedService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageReceived> getAllEntries() {
        return messageReceivedService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageReceived createMessageReceived(@Valid @RequestBody MessageReceived messageReceived) {
        return messageReceivedService.createMessageReceived(messageReceived);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteMessageReceived(@Valid @RequestBody MessageReceived messageReceived) { messageReceivedService.deleteMessageReceived(messageReceived); }
}
