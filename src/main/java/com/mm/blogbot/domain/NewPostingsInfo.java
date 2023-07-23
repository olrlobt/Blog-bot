package com.mm.blogbot.domain;

import java.util.List;
import lombok.Data;

@Data
public class NewPostingsInfo {

    private List<PostingInfo> newPostingInfos;

    public NewPostingsInfo(List<PostingInfo> newPostingInfos) {
        this.newPostingInfos = newPostingInfos;
    }
}
