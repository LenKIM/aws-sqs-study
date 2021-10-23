package org.example.sqs.springcloud;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.core.SqsMessageHeaders;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Configuration
public class SqsQueueSenderWithType {

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final String SQS_JK_PERSON_NAME = "TEST-JK-PERSON";

    public QueueMessagingTemplate getQueueMessagingTemplate(){
        AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Autowired
    public SqsQueueSenderWithType() {
        this.queueMessagingTemplate = this.getQueueMessagingTemplate();
    }

    public void send(Person person) {
        this.queueMessagingTemplate.convertAndSend(SQS_JK_PERSON_NAME, person);
    }

    @MessageMapping("TEST-JK-PERSON")
    void personQueue(Person message , @Headers SqsMessageHeaders headers) {
        System.out.println("TEST-JK-PERSON > " + message.toString());
    }

    @Data
    public static class Person {
        private String name;
        private int age;
        private String currentTime;
    }

}
