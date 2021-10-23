package org.example.sqs.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;


@SpringJUnitConfig(classes = SqsQueueSenderWithType.class)
class SqsQueueSenderWithTypeTest {

    @Autowired
    SqsQueueSenderWithType sqsQueueSenderWithType;

    @Test
    void publish() {
        SqsQueueSenderWithType.Person person = new SqsQueueSenderWithType.Person();
        person.setName("JK");
        person.setAge(10);
        person.setCurrentTime(LocalDateTime.now().toString());

        sqsQueueSenderWithType.send(person);
    }
}
