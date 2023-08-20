package com.mm.blogbot.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "member")
public class MemberInfo {

    private List<BlogInfo> blogInfos;

    public void addBlogInfo(BlogInfo blog){
        blogInfos.add(blog);
    }

    public MemberInfo() {
        blogInfos = new ArrayList<>();
    }
}
