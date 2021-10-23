Amazon SQS, Amazon MQ 및 Amazon SNS 차이점



Amazon SQS 및[Amazon SNS](http://aws.amazon.com/sns/)는 확장성이 우수하고 사용하기 쉬우며 메시지 브로커를 설정할 필요가 없는 대기열 및 주제 서비스입니다. 이러한 서비스는 무제한에 가까운 확장성과 간편한 API를 활용할 수 있는 새로운 애플리케이션에 사용하면 좋습니다.

[Amazon MQ](http://aws.amazon.com/amazon-mq/)는 널리 사용되는 다양한 메시지 브로커와 호환되는 관리형 메시지 브로커 서비스입니다. JMS 등과 같은 API 또는 AMQP, MQTT, OpenWire 및 STOMP와 같은 프로토콜과 호환되는 기존 메시지 브로커의 애플리케이션을 마이그레이션할 때 Amazon MQ를 사용하면 좋습니다.



메세지 브로커 존재여부가 가장 크다.



## Amazon SQS

### 분산 대기열

분산 대기열이며, 3가지 구성 요소를 가진다.

1. 분산 시스템의 구성 요소
2. 대기열(Amazon SQS 서버에 분산됨)
3. 대기열의 메시지

 여러 생산자(메시지를 대기열로 전송하는 구성 요소) 및 소비자(대기열의 메 시지를 수신하는 구성 요소)가 있습니다. 대기열 (메시지 A~E 유지) 은 여러 Amazon SQS 서버에 메시지를 중복 저장

![image-20211023121441906](https://tva1.sinaimg.cn/large/008i3skNgy1gvp2ohhnynj61320h0myk02.jpg)

### 메시지 수명 주기

![image-20211023121652446](https://tva1.sinaimg.cn/large/008i3skNgy1gvp2qom6ecj60u010t44002.jpg)



오호.. **제한 시간 초과**  라는 것은 메세지를 전달받고나서도 일정시간 메세지가 큐에 남아있나보군?

SQS 대기열은 최소 1회의 메세지 전송을 지원하지만, 2개 이상의 메시지 사본이 순서가 지정되지 않게 전송될 수 있다.

**만약 두 번 이상 순서에 부합하지 않게 도착하는 메세지를 처리하는 경우에는. 표준 메세지 대기열을 사용하라.** 그렇지 않고, 이벤트 순서나 중복 항목이 허용되지 않는 경우에는 FIFO 를 사용하라. FIFO 를 사용할 경우, 문자열에 접미사로 FIFO 를 적용할 수 있다.

---



https://sqs.us-east-2.amazonaws.com/123456789012/MyQueue 여기서 `1234...` 는 AWS 계정번호를 의미하는 구나.



**메세지 메타데이터?**

구조화된 메타데이터를 사용하여 메세지에 포함할 수 있다.

각 메시지에는 최대 10개의 속성이 있을 수 있다. 

어떤 것을 넣을 수 있을까?

메시지 속성 데이터 형식을 넣을 수 있다.

![image-20211023131157598](https://tva1.sinaimg.cn/large/008i3skNgy1gvp4c4orwij612m0pkgtc02.jpg)

--

추후 비용이 발생할 수 있기 때문에 비용할당 태그를 추가할수 있다.

![image-20211023131437342](https://tva1.sinaimg.cn/large/008i3skNgy1gvp4et7e9oj60u00w5ag902.jpg)



---

**Amazon SQS 배달 못한 편지 대기열**

배달못한(= 소비하지 못한) 메시지를 구분하여 배달못한 편지 대기열에 삽입할 수 있다. 이는 애플/메시지 디버깅에 유용하다.



그런데- 어떻게 배달하지 못했다를 구분하지?

> 배달 못한 편지 대기열의 작동 방식

Retrieve 정책을 지정할 수 있는데- 메세지 처리에 실패한 횟수가 정해진 숫자에 도달하면 SQS가 메세지를 이전 대기열에서 배달 못한 편지 대기열에 옮기도록 합니다.

 예를 들어, 소스 대기열에 리드라 이브 정책이 있는 경우maxReceiveCount를 5로 설정하고 소스 대기열의 소비자가 메시지를 6번 받았는데 한 번도 삭제하지 못하는 경우 Amazon SQS 가 메시지를 배달 못한 편지 대기열로 옮깁니다.

- 같은 리전에 있어야 한다. 

![image-20211023133300269](https://tva1.sinaimg.cn/large/008i3skNgy1gvp4xx7kmkj61860fcafl02.jpg)

![image-20211023133419658](https://tva1.sinaimg.cn/large/008i3skNgy1gvp4zb6bccj617g0l27az02.jpg)

---

SQS 에서는 `Visibilty Timeout` 이라는 것이 존재해서 수신하더라도 일정시간 남아있을 수 있다.

![](https://tva1.sinaimg.cn/large/008i3skNgy1gvp51wwljdj618u0mqdkm02.jpg)

![image-20211023134603520](https://tva1.sinaimg.cn/large/008i3skNgy1gvp5bhxcqlj61ay0b078k02.jpg)

