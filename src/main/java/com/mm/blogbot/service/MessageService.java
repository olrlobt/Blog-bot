package com.mm.blogbot.service;

import com.mm.blogbot.domain.BlogInfo;
import com.mm.blogbot.domain.MemberInfo;
import com.mm.blogbot.domain.PostingInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
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

    public void sendMessage(MemberInfo memberInfo) {

        requestWebhooks(makeRequest(memberInfo));
    }

    private MultiValueMap<String, String> makeRequest(MemberInfo memberInfo){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        JSONObject payloadMessage = new JSONObject();
        JSONArray attachmentsArray = new JSONArray();

        for (BlogInfo blogInfo : memberInfo.getBlogInfos()) {
            if (blogInfo.getPostingInfos().size() == 0) {
                continue;
            }
            attachmentsArray.put(makeMessage(blogInfo));
        }
        payloadMessage.put("attachments", attachmentsArray);
        requestBody.add("payload", payloadMessage.toString());
        return requestBody;
    }

    private JSONObject makeMessage(BlogInfo blogInfo) {
        JSONObject jsonAttachment = new JSONObject();
        jsonAttachment.put("fallback", blogInfo.getAuthor() + "의 새로운 포스팅");
        jsonAttachment.put("color", blogInfo.getColor());
        jsonAttachment.put("author_name", blogInfo.getAuthor());

        JSONArray fieldArray = new JSONArray();
        for (PostingInfo postingInfo : blogInfo.getPostingInfos()) {
            JSONObject jsonFields = new JSONObject();
            StringBuilder sb = new StringBuilder();
            sb.append("##### [").append(postingInfo.getTitle()).append("](").append(postingInfo.getLink()).append(")");
            jsonFields.put("short", false);
            jsonFields.put("value", sb);
            fieldArray.put(jsonFields);
        }
        jsonAttachment.put("fields", fieldArray);
        return jsonAttachment;
    }

    private void requestWebhooks(MultiValueMap<String, String> requestBody) {
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

        if (statusCode.is2xxSuccessful()) {
            log.info("전송 성공");
        } else {
            log.error("전송 실패");
        }
    }


    /*
    private void sendMessageOfBot(PostingInfo postingInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded; charset=UTF-8"));
        headers.set("Authorization", "Bearer " + TOKEN_BOT);

        JSONObject json = new JSONObject();
        json.put("message", "## " + postingInfo.getTitle() + "\n" + postingInfo.getLink());
        json.put("channel_id", TOKEN_CHANNEL);

        HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);

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
    */
}
