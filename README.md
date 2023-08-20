# M.M. Blog-bot

<br>SSAFY 10기_17반 기술 블로그 스터디에서 사용하는 MatterMost 블로그 크롤링 봇
<br>
<br>
<br>

1. Jsoup 라이브러리 - 블로그 크롤링
    - 현재 테스트 된 블로그 : 
      - tistory
      - velog
2. GithubActions - 자동화
   - 매일 오전 11시 작동




<hr> 

### 사용법


[Base64 인코딩](https://www.convertstring.com/ko/EncodeDecode/Base64Encode) 

1. Github Secret에 M.M. Webhook URL을 등록한다. 양식은 아래를 따른다.
<br> (** Base64로 인코딩 하여 사용한다 **)

Secret Name : SECRET_APP
```yaml
SSAFY : https://***.***.com/***/*******
```


2. 블로그 URL을 등록한다. 양식은 아래를 따른다.
   <br> (** Base64로 인코딩 하여 사용한다 **)

Secret Name : BLOG_URLS
```yaml
member:
  blogInfos:
    - author: "손흥민"
      color: "#AC5EFF"
      link: "https://{id}.tistory.com/rss"
    - author: "BTS"
      color: "#58C972"
      link: "https://{id}.tistory.com/rss"
    - author: "봉준호"
      color: "#F08650"
      link: "https://{id}.tistory.com/rss"
    - author: "이승헌"
      color: "#EB3324"
      link: "https://v2.velog.io/rss/{id}"
```

<br>
<br>
<hr> 

### 작동

<img width="528" alt="image" src="https://github.com/olrlobt/Blog-bot/assets/99643732/d74d026c-9ffc-4b0f-9b63-d1426d514f34">


```text
    {저자}
    {Posting 1}
    {Posting 2}
    ---
    {저자2}
    {Posting 1}
    ...
```

<br>
<br>
<hr> 

### Update Note



- 08/20 추가
  - 최신 포스팅을 1개 이상 가져올 수 있도록 수정

