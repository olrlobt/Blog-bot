package com.mm.blogbot.domain;

import java.util.ArrayList;
import lombok.Data;

import java.util.List;

@Data
public class BlogInfo {

    private List<PostingInfo> postingInfos;
    private String author;
    private String link;
    private String color;

    public void addPosting(PostingInfo post){
        postingInfos.add(post);
    }

    public BlogInfo() {
    }

    public BlogInfo(String author, String color) {
        postingInfos = new ArrayList<>();
        this.author = author;
        this.color = color;
    }
}
