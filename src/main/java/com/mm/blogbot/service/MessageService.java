package com.mm.blogbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {

    @Value("${server.url}")
    private String SERVER_URL;
    @Value("${token.user}")
    private String TOKEN_USER;
    @Value("${token.bot}")
    private String TOKEN_BOT;
    @Value("${token.channel}")
    private String TOKEN_CHANNEL;

    public void sendMessageOfUser() {
        // 요청 본문에 포함할 데이터
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("payload", "{\"text\": \"Hello, this is some text.\\nThis is more text.\"}");

        // 요청 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                SERVER_URL + "hooks/" + TOKEN_USER,
                HttpMethod.POST,
                requestEntity,
                String.class);

        // 응답 출력
        HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);
    }


    public void sendMessageOfBot() {

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TOKEN_BOT);

        // Set request body
        String jsonBody = "{\"message\": \"test message by bot token\", \"channel_id\":\"" + TOKEN_CHANNEL + "\"}";

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                SERVER_URL + "api/v4/posts",
                HttpMethod.POST,
                requestEntity,
                String.class);

        // Get the response status code and body
        HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        // Print the response
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);
    }
}
