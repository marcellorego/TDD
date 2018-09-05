package br.com.tests.controller;

import br.com.tests.controller.service.IMessageService;
import br.com.tests.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EchoController {

    @Autowired
    private IMessageService messageService;

    @GetMapping(path = "/echo/{message}")
    public Message echoMessage(@PathVariable("message") String message) {
        return new Message(message);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message createUser(@RequestBody Message message) {
        Message messageCreated = messageService.create(message);
        return messageCreated;
    }
}
