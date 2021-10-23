package org.example.sqs.springcloud.api;

import lombok.RequiredArgsConstructor;
import org.example.sqs.springcloud.SqsQueueSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SqsCloudApi {

    private final SqsQueueSender sqsQueueSender;

    @GetMapping(name = "/send")
    public ResponseEntity<String> helloWorld(
            @RequestParam("msg") String msg
    ){
        sqsQueueSender.send(msg);
        return ResponseEntity.ok("ok");
    }


}
