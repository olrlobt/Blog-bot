package com.mm.blogbot.controller;

import com.mm.blogbot.service.CrawlingService;
import com.mm.blogbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class BlogBotController {

    @Autowired
    private MessageService botMessageService;

    @Autowired
    private CrawlingService crawlingService;

    @GetMapping("/send")
    @ResponseBody
    public void sendMessageOfUser() {
        botMessageService.sendMessageOfUser();
    }

    @GetMapping("/bot")
    @ResponseBody
    public void sendMessageOfBot() {
        botMessageService.sendMessageOfBot();
    }

    @GetMapping("/post")
    @ResponseBody
    public void getPostTitle() throws IOException {
        crawlingService.getPost();
    }

}
