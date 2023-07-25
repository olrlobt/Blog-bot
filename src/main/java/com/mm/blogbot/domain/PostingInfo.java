package com.mm.blogbot.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostingInfo {

    private String title;
    private String link;
    private LocalDateTime time;

    public PostingInfo(String title, String link, LocalDateTime time) {
        this.title = title;
        this.link = link;
        this.time = time;
    }
}
