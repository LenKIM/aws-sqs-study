package org.example.sqs.basic;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.BytesWrapper;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SQS
 * SQS 큐에 메시지를 보내는 방법은 다음과 같습니다.
 *
 * MessageBody: 메시지 내용입니다.
 * QueueUrl: 메시지를 보낼 SQS 큐 URL입니다.
 * DelaySeconds: 지연 전송 시간입니다.
 * MessageAttributes: 메시지 추가 속성입니다.
 *     - DataType: String, Number, Binary를 사용할 수 있습니다.
 *     - StringValue: DataType이 String, Number일 때 사용합니다.
 *     - BinaryValue: DataType이 Binary일 때 사용하며 Buffer 형식으로 값을 설정합니다.
 */
@RestController
@RequiredArgsConstructor
public class SendApiTest {

    private final SqsClient sqsClient;
    private static final String URL = "QUEUE_URL"; //TODO Write your own QUEUE_URL


    @Value(staticConstructor = "of")
    static class Name {
        String value;
    }

    @GetMapping(value = "sendApi/sendWithMsgAttr")
    public ResponseEntity<String> sender(
            @RequestParam(value = "msg") String meg
    ) {

        HashMap<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("attr1",
                MessageAttributeValue
                        .builder()
                        .dataType("Number")
                        .stringValue("1234")
                        .build()
        );
//        messageAttributes.put("attr2",
//                MessageAttributeValue
//                        .builder()
//                        .stringValue("Test").build());
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(URL)
                .messageBody(meg)
                        .messageAttributes(messageAttributes)
                .build());
        return ResponseEntity.ok("ok");

    }
}

