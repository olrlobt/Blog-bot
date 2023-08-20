package com.mm.blogbot;

import com.mm.blogbot.domain.MemberInfo;
import com.mm.blogbot.service.CrawlingService;
import java.io.IOException;

import com.mm.blogbot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(MemberInfo.class)
public class BlogBotApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(BlogBotApplication.class, args);
		CrawlingService crawlingService = context.getBean(CrawlingService.class);
		MemberInfo newPosts = crawlingService.getNewPost();

		if (newPosts.getBlogInfos().stream().noneMatch(blogInfo -> blogInfo.getPostingInfos().size() != 0)) {
			log.info("None Match");
			exit(context);
			return;
		}

		MessageService messageService = context.getBean(MessageService.class);
		messageService.sendMessage(newPosts);
		log.info("Success");
		exit(context);
	}

	private static void exit(ApplicationContext context){
		((ConfigurableApplicationContext) context).close();
	}
}
