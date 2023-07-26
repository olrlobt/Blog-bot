package com.mm.blogbot;

import com.mm.blogbot.domain.BlogInfo;
import com.mm.blogbot.service.CrawlingService;
import java.io.IOException;

import com.mm.blogbot.service.MessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(BlogInfo.class)
public class BlogBotApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(BlogBotApplication.class, args);

		CrawlingService crawlingService = context.getBean(CrawlingService.class);
		BlogInfo newPost = crawlingService.getNewPost();

		if (newPost.getBlogs().size() == 0) {
			exit(context);
		}

		MessageService messageService = context.getBean(MessageService.class);
		messageService.sendMessage(newPost);
		exit(context);
	}

	private static void exit(ApplicationContext context){
		((ConfigurableApplicationContext) context).close();
	}
}
