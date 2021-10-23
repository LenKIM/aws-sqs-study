package org.example.sqs.springcloud;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

@Configuration
public class SqsQueueSender {

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final String SQS_QUEUE_NAME = "TEST-JK-QUEUE";

    public QueueMessagingTemplate getQueueMessagingTemplate(){
        AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder
                .defaultClient();
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Autowired
    public SqsQueueSender() {
        this.queueMessagingTemplate = this.getQueueMessagingTemplate();
    }

    public void send(String message) {
        this.queueMessagingTemplate.send(SQS_QUEUE_NAME, MessageBuilder.withPayload(message)

                        .build());
    }

    public void receive() {
        Message<?> receive = this.queueMessagingTemplate.receive(SQS_QUEUE_NAME);
        System.out.println(receive);
    }

    /**
     * @MessageMapping
     * https://tva1.sinaimg.cn/large/008i3skNgy1gvpevguzfwj61sr0u0n7e02.jpg
     * @param message
     */
    @MessageMapping("TEST-JK-QUEUE")
    public void queueListener(String message, @Headers Map headers) {
        System.out.println("TEST-JK-QUEUE > " + message);
    }
}
