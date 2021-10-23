
코드를 실행시키기 위해서는 `AWS_ACCESS_KEY`, `AWS_ACCESS_SECRET_KEY`를 System 설정에 저장되어야 합니다.
또는 실행할 때 아래와 같은 설정을 해야 합니다.

![](https://tva1.sinaimg.cn/large/008i3skNgy1gvphky7zk7j61ck0u077m02.jpg)


**공식 사이트**

[https://awspring.io](https://awspring.io)


SQS 간략 설명 - http://pyrasis.com/book/TheArtOfAmazonWebServices/Chapter30/13



**읽을 거리**

 - 공식문서 - https://docs.aws.amazon.com/ko_kr/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-dg.pdf#page=81&zoom=100,96,384  
  
 - 어떻게 처리? https://namkyujin.com/post/20191023-amazon-sqs-dlq/  

 - 처리못한 메세지 Slack 으로 보내기 -https://ichi.pro/ko/slackeul-sayonghayeo-sqs-baedal-moshan-pyeonji-daegiyeol-moniteoling-8486161652924  

 - 어떻게 DLQ 를 테스트하지? https://lannstark.tistory.com/87





**troubleshooting**

- SqsListener String index out of bounds issue > https://stackoverflow.com/questions/52319894/sqslistener-string-index-out-of-bounds-issue
