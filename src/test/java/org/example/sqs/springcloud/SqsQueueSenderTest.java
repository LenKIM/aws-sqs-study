package org.example.sqs.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = SqsQueueSender.class)
class SqsQueueSenderTest {

    @Autowired
    SqsQueueSender sqsQueueSender;

    @Test
    void publish() {
        sqsQueueSender.send("xxx");

    }
}
