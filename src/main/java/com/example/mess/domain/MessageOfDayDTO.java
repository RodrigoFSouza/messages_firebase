package com.example.mess.domain;

import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MessageOfDayDTO {
    private UUID id;
    private Long code;
    private String text;
    private String source;
    private LocalDateTime displayDate;
}
