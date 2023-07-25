package com.mm.blogbot;

import com.mm.blogbot.domain.BlogInfo;
import com.mm.blogbot.domain.NewPostingsInfo;
import com.mm.blogbot.service.CrawlingService;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(BlogInfo.class)
public class BlogBotApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(BlogBotApplication.class, args);
		CrawlingService crawlingService = context.getBean(CrawlingService.class);
		NewPostingsInfo newPost = crawlingService.getNewPost();
//		try {
////		crawlingService.getPost();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
//
//	private static CrawlingService generateService() throws IOException {
//
//		return new CrawlingService( new BlogInfo());
//	}
//
//	private static NewPostingsInfo readPosting() throws IOException {
//
//		return new NewPostingsInfo();
//	}
//
//	private static void addBlogLink() throws IOException {
//
//	}
}
