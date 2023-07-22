package com.mm.blogbot.domain;


import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "blog")
public class BlogInfo {

    private List<String> urls;
}
