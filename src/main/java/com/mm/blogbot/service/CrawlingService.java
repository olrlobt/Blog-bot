package com.mm.blogbot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class CrawlingService {

    private final String URL = "https://olrlobt.tistory.com/rss";
    @Value("${blog.title}")
    private String titleClassName;
    @Value("${blog.date}")
    private String dateClassName;


    public void getPost() throws IOException {
        Document document = Jsoup.connect(URL).get();
        getPostTitle(document);
        getPostDate(document);
    }


    private String getPostTitle(Document document) throws IOException {
        Elements titles = document.select(titleClassName);
        return titles.get(0).text();
    }


    private String getPostDate(Document document) {
        Elements dates = document.select(dateClassName);
        try {
            String inputDateStr = dates.get(0).text();
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = inputFormat.parse(inputDateStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            // UTC 시간대로 설정
            outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





}
