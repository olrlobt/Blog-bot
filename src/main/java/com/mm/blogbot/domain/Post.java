package com.mm.blogbot.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Post {

    private final String title;
    private final String link;
    private final String summary;
    private final LocalDate date;

    public Post(String title, String link, String summary, LocalDate date) {
        this.title = title;
        this.link = link;
        this.summary = summary;
        this.date = date;
    }
}
