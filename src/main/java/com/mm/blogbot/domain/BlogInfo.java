package com.mm.blogbot.domain;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "member")
public class BlogInfo {

    private List<PostingInfo> blogs;

    public BlogInfo() {
    }

    public BlogInfo(List<PostingInfo> blogs) {
        this.blogs = blogs;
    }
}
/////////////////