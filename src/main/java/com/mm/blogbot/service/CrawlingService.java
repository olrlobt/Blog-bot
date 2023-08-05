package com.mm.blogbot.service;

import com.mm.blogbot.domain.BlogInfo;
import com.mm.blogbot.domain.PostingInfo;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@Slf4j
public class CrawlingService {

    @Value("${blog.title}")
    private String titleClassName;
    @Value("${blog.date}")
    private String dateClassName;
    @Value("${blog.link}")
    private String linkClassName;
    private final BlogInfo blogInfo;

    @Autowired
    public CrawlingService(BlogInfo blogInfo) {
        this.blogInfo = blogInfo;
        log.info("bloginfo = {}", blogInfo);
    }

    public BlogInfo getNewPost() throws IOException {
        BlogInfo newPostingsInfo = new BlogInfo(new ArrayList<>());

        if(blogInfo == null){
            return newPostingsInfo;
        }

        for (PostingInfo blog : blogInfo.getBlogs()) {
            PostingInfo newPosting = getNewPostInfo(blog.getLink() , blog.getAuthor());
            if (newPosting != null) {
                newPostingsInfo.getBlogs().add(newPosting);
            }
        }
        log.info("new posting size = {}", newPostingsInfo.getBlogs().size());
        return newPostingsInfo;
    }

    private PostingInfo getNewPostInfo(String url, String author) throws IOException {
        Document document = Jsoup.connect(url).get();
        ZonedDateTime postDate = null;
        if(url.contains("tistory")){
            postDate = getPostDateForTistory(document);
        }else if(url.contains("velog")){
            postDate = getPostDateForVelog(document);
        }

        if (postDate == null || !validNewPost(postDate)) {
            log.info(url + " 에는 최신 포스팅 없음");
            return null;
        }
        return new PostingInfo(author , getPostTitle(document), getPostLink(document), postDate);
    }

    private boolean validNewPost(ZonedDateTime postDate) {
        ZonedDateTime nowZone = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration duration = Duration.between(postDate, nowZone);

        return duration.toDays() < 1;
    }

    private String getPostTitle(Document document) {
        Elements titles = document.select(titleClassName);
        return titles.get(0).text();
    }

    private String getPostLink(Document document) {
        Elements link = document.select(linkClassName);
        return link.get(0).text();
    }

    private ZonedDateTime getPostDateForTistory(Document document) {
        Elements dates = document.select(dateClassName);
        if(dates.isEmpty()){
            return null;
        }

        String dateString = dates.get(0).text();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        return ZonedDateTime.parse(dateString, inputFormatter);
    }

    private ZonedDateTime getPostDateForVelog(Document document) {
        Elements dates = document.select(dateClassName);
        if (dates.isEmpty()) {
            return null;
        }

        String dateString = dates.get(0).text();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH).withZone(ZoneId.of("GMT"));
        Instant instant = Instant.from(inputFormatter.parse(dateString));
        return instant.atZone(ZoneId.systemDefault());
    }

}
