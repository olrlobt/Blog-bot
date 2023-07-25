package com.mm.blogbot.service;

import com.mm.blogbot.domain.NewPostingsInfo;
import com.mm.blogbot.domain.PostingInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MessageService {

    @Value("${SSAFY}")
    private String SERVER_SSAFY;


    public void sendMessage(NewPostingsInfo newPost){
        for(PostingInfo postingInfo : newPost.getNewPostingInfos()){
//            sendMessageOfBot(postingInfo);
            sendMessageOfUser(postingInfo);
        }
    }

    public void sendMessageOfUser(PostingInfo postingInfo) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        JSONObject json = new JSONObject();
        json.put("text", "## " + postingInfo.getTitle() + "\n" + postingInfo.getLink());

        requestBody.add("payload", json.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                SERVER_SSAFY,
                HttpMethod.POST,
                requestEntity,
                String.class);


        HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();
        log.info("Status code: {}" , statusCode);
        log.info("Response body: {}" , responseBody);
    }

//    private void sendMessageOfBot(PostingInfo postingInfo) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=UTF-8"));
//        headers.set("Authorization", "Bearer " + TOKEN_BOT);
//
//        JSONObject json = new JSONObject();
//        json.put("message", "## " + postingInfo.getTitle() + "\n" + postingInfo.getLink());
//        json.put("channel_id", TOKEN_CHANNEL);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
//
//        // Create RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Send the POST request
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                SERVER_URL + "api/v4/posts",
//                HttpMethod.POST,
//                requestEntity,
//                String.class);
//
//        // Get the response status code and body
//        HttpStatus statusCode = responseEntity.getStatusCode();
//        String responseBody = responseEntity.getBody();
//
//        // Print the response
//        System.out.println("Status code: " + statusCode);
//        System.out.println("Response body: " + responseBody);
//    }

}
