package com.mm.blogbot.service;

import com.mm.blogbot.domain.BlogInfo;
import com.mm.blogbot.domain.MemberInfo;
import com.mm.blogbot.domain.PostingInfo;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrawlingService {

    @Value("${blog.title}")
    private String titleClassName;
    @Value("${blog.date}")
    private String dateClassName;
    @Value("${blog.link}")
    private String linkClassName;

    private final MemberInfo memberInfo;

    @Autowired
    public CrawlingService(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
        for (BlogInfo blogInfo : memberInfo.getBlogInfos()) {
            log.info("블로그 리스트 = {}", blogInfo);
        }
    }

    public MemberInfo getNewPost() throws IOException {
        MemberInfo newPostsInfo = new MemberInfo();

        if(memberInfo == null){
            log.info("블로그 정보 로드 실패");
            return null;
        }

        for (BlogInfo blog : memberInfo.getBlogInfos()) {
            newPostsInfo.addBlogInfo(getNewPostInfo(blog));
        }
        return newPostsInfo;
    }

    private BlogInfo getNewPostInfo(BlogInfo blog) throws IOException {
        Document document = Jsoup.connect(blog.getLink()).get();
        ZonedDateTime postDate = null;
        BlogInfo postingInfos = new BlogInfo(blog.getAuthor(), blog.getColor());

        int index= -1;
        while (validNewPost(postDate = parsePostDate(getPostElements(document, dateClassName, ++index)))) {
            postingInfos.addPosting(new PostingInfo(getPostElements(document,titleClassName, index), getPostElements(document,linkClassName, index), postDate));
        }
        if (postingInfos.getPostingInfos().size() == 0) {
            log.info(blog.getLink() + " 에는 최신 포스팅 없음");
        }
        return postingInfos;
    }

    private boolean validNewPost(ZonedDateTime postDate) {
        ZonedDateTime nowZone = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration duration = Duration.between(postDate, nowZone);

        return duration.toDays() < 1;
    }
    private String getPostElements(Document document , String elementsName , int index) {
        Elements elements = document.select(elementsName);
        if(elements.isEmpty()){
            return null;
        }
        return elements.get(index).text();
    }

    private ZonedDateTime parsePostDate(String dateString) {
        if (dateString.endsWith("GMT")) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH).withZone(ZoneId.of("GMT"));
            Instant instant = Instant.from(inputFormatter.parse(dateString));
            return instant.atZone(ZoneId.systemDefault());
        } else {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            return ZonedDateTime.parse(dateString, inputFormatter);
        }
    }

}
