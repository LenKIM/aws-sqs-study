package org.example.sqs.basic;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BasticApiTest {

    private final SqsClient sqsClient;
    private static final String URL = "QUEUE_URL"; //TODO Write your own QUEUE_URL

    @GetMapping(value = "/getQueueUrls")
    public ResponseEntity<String> getQueueUrls() {
        ListQueuesRequest build = ListQueuesRequest.builder().build();
        List<String> strings = sqsClient.listQueues(build).queueUrls();

        return ResponseEntity.ok(String.join("<br>", strings));
    }

    @GetMapping(value = "/getSingleQueueUrls")
    public ResponseEntity<String> getSingleQueueUrls() {
        GetQueueUrlRequest request = GetQueueUrlRequest.builder().queueName("TEST-JK-QUEUE").build();
        GetQueueUrlResponse queueUrl = sqsClient.getQueueUrl(request);

        return ResponseEntity.ok(queueUrl.queueUrl());
    }

    @GetMapping(value = "/send")
    public ResponseEntity<String> sender(
            @RequestParam(value = "msg") String meg
    ) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(URL)
                .messageBody(meg)
                .delaySeconds(10)
                .build());
        return ResponseEntity.ok("ok");
    }

    /**
     * 전송하고 삭제하지 않음.
     *
     * @return
     */
    @SneakyThrows
    @GetMapping(value = "/searchMessageTest")
    public ResponseEntity<String> senderAndSearchMessages() {
        String prefix = "searchMessageTest > ";
        for (int i = 0; i < 10; i++) {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(URL)
                    .messageBody(prefix + i)
                    .build());
        }
        Thread.sleep(5000L);

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(URL)
                .maxNumberOfMessages(10)
                .build();

        return ResponseEntity.ok(sqsClient.receiveMessage(request).messages()
                .stream()
                .map(Message::toString)
                .collect(Collectors.joining("<br><br>"))
        );
    }

    /**
     * 전송하고 삭제함.
     * 대기열 보면 아무것도 없음.
     */
    @SneakyThrows
    @GetMapping(value = "/searchMessageAndDelete")
    public ResponseEntity<String> senderAndReceiveWithRemove() {
        String prefix = "searchMessageAndDelete > ";
        for (int i = 0; i < 10; i++) {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(URL)
                    .messageBody(prefix + i + LocalDateTime.now())
                    .build());
        }
        // 비동기로 동작하기 때문에 초당 삭제되는 횟수가 정해짐
        Thread.sleep(5000L);

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(URL)
                .maxNumberOfMessages(10)
                .build();

        sqsClient.receiveMessage(request).messages()
                .forEach(message -> {
                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(URL)
                            .receiptHandle(message.receiptHandle())
                            .build();
                    DeleteMessageResponse deleteMessageResponse = sqsClient.deleteMessage(deleteMessageRequest);
                });
        return ResponseEntity.ok("ok");
    }
}

