package com.mm.blogbot.service;

import com.mm.blogbot.domain.BlogInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private final String URL = "https://olrlobt.tistory.com/rss";
    @Value("${blog.title}")
    private String titleClassName;
    @Value("${blog.date}")
    private String dateClassName;

    private BlogInfo blogInfo;

    @Autowired
    public CrawlingService(BlogInfo blogInfo) {
        this.blogInfo = blogInfo;
        log.info("bloginfo = {}", blogInfo);
    }


    public void getPost() throws IOException {
        Document document = Jsoup.connect(URL).get();
        getPostTitle(document);

        System.out.println(getPostDate(document));
    }

    private String getPostTitle(Document document) {
        Elements titles = document.select(titleClassName);
        return titles.get(0).text();
    }

    private String getPostDate(Document document) {
        Elements dates = document.select(dateClassName);
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = inputFormat.parse(dates.get(0).text());
            return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
