package com.mm.blogbot.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CrawlingService {

    public void getPostTitle() throws IOException {
        String URL = "https://olrlobt.tistory.com/rss";

        Connection conn = Jsoup.connect(URL);
        Document document = conn.get();

        Elements items = document.select("item title");
        for (Element title : items) {
            System.out.println(title.text());
        }
    }
}
