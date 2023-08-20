package com.mm.blogbot.domain;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class PostingInfo {
    private String title;
    private String link;
    private ZonedDateTime time;

    public PostingInfo() {
    }

    public PostingInfo(String title, String link, ZonedDateTime time) {
        this.title = title;
        this.link = link;
        this.time = time;
    }

}
