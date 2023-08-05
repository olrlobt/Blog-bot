package com.mm.blogbot.domain;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class PostingInfo {

    private String author;
    private String title;
    private String link;
    private ZonedDateTime time;

    public PostingInfo(String author, String title, String link, ZonedDateTime time) {
        this.author = author;
        this.title = title;
        this.link = link;
        this.time = time;
    }
}
