package br.com.tests.controller.service.impl;

import br.com.tests.controller.service.IMessageService;
import br.com.tests.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {

    @Override
    public Message create(Message message) {
        return message;
    }
}
