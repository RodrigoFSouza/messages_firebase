package com.example.mess.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageOfDay {
    private String id;
    private String text;
    private String source;
    private String displayDate;
}
