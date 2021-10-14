package com.example.mess.controller;

import com.example.mess.domain.MessageOfDay;
import com.example.mess.service.MessageOfDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageOfDayController {

    public static final int DEFAULT_NUMBER_DISPLAY_PREVIOUS = 7;
    public static final int START_DISPLAY_PREVIOUS = 0;
    private MessageOfDayService messageOfDayService;

    @Autowired
    public MessageOfDayController(MessageOfDayService messageOfDayService) {
        this.messageOfDayService = messageOfDayService;
    }

    @GetMapping("/message_of_day")
    public MessageOfDay messageOfDay() {
        return messageOfDayService.messageOfDay();
    }

    @GetMapping("/previous_messages")
    public List<MessageOfDay> previousList(@RequestParam Integer start, @RequestParam Integer end) {
        if (Objects.isNull(end)) {
            end = DEFAULT_NUMBER_DISPLAY_PREVIOUS;
        }
        if (Objects.isNull(start)) {
            start = START_DISPLAY_PREVIOUS;
        }
        return messageOfDayService.previousMessages(start, end);
    }
}
