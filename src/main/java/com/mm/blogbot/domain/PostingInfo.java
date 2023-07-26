package com.mm.blogbot.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostingInfo {

    private String author;
    private String title;
    private String link;
    private LocalDateTime time;

    public PostingInfo() {
    }

    public PostingInfo(String author, String title, String link, LocalDateTime time) {
        this.author = author;
        this.title = title;
        this.link = link;
        this.time = time;
    }
}
