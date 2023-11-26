# 스프링 카프카를 활용한 회원 등록과 쿠폰 등록 
**학습 목적**
- 멀티 모듈의 이해.
- 스프링 카프카를 이용한 카프카 프로듀서와 컨슈머의 이해.

## 개발 환경
- Mac OS(M2, 32G)
- Oracle cloud(4core, 24G)

## 프로젝트 기술 스택
- java 11
- spring boot 2.7.15
- spring kafka 2.8.11
- spring data jpa 2.7.15
- spring data redis 2.7.15
- MySQL 8

## 프로젝트 아키텍쳐
![스크린샷 2023-11-25 오후 9 41 19](https://github.com/TeamRocketDan/.github/assets/67041069/3792a14a-32c2-4129-a85e-79cba451e4fc)

## 멀티 모듈 구조
- module-core에서 모든 의존성을 갖게 되면 모듈이 점점 비대해져 재사용성이 떨어질 가능성이 있다고 생각되어 **모듈도 하나의 책임만 가질 수 있도록 설계.**
- 모든 CRUD query가 module-core에 작성되는 것은 모듈의 가독성이 떨어질 가능성이 있다고 생각되어 특정 애플리케이션에서만 자주 사용되는 query는 애플리케이션 모듈에서 작성하도록 함.

<img width="522" alt="스크린샷 2023-11-26 오후 2 24 47" src="https://github.com/rbsks/kafka-practice/assets/67041069/882bef6c-1c18-412d-8190-df5d63e4b46e">

**module-consumer의 build.gradle**
<img width="1012" alt="스크린샷 2023-11-25 오후 10 30 37" src="https://github.com/TeamRocketDan/backend_server_chat/assets/67041069/784b9eba-5835-40e4-96fd-40a030ea2767"></br></br>
**module-producer의 build.gradle** 
<img width="1012" alt="스크린샷 2023-11-25 오후 10 30 10" src="https://github.com/TeamRocketDan/backend_server_chat/assets/67041069/afd198e2-70aa-47ac-bd91-03b60a3b16fb">

## 카프카 클러스터
**broker**</br>
- 카프카 브로커 3대와 주키퍼 3대를 사용하여 클러스터 구축.</br>
- **학습을 위해 하나의 인스턴스에 도커를 사용하여 카프카 클러스터를 구축했지만 실제 운영 환경에서는 브로커 한 대당 서버 한 대가 적합.**
![스크린샷 2023-11-25 오후 10 44 07](https://github.com/rbsks/kafka-practice/assets/67041069/2d65e199-59b1-4c9f-b3be-a582991dd07a)</br>

**producer**</br>
- 카프카 클라이언트에서 프로듀서가 send() 메서드를 통해 레코드를 브로커로 전송하게 되면 **send -> serializer -> partitioner -> recode accumulator** 순서로 레코드가 recode accumulator 내부에 존재하는 토픽의 파티션 배치 영역에 저장 됨.
- 배치 영역에 저장된 레코드는 프로듀서의 batch.size와 linger.ms 설정 값에 따라 브로커로 바로 전송될 수도 있고 그렇지 않을 수도 있음.
- linger.ms의 default 값은 0ms이므로 이 값을 따로 설정해 주지 않는다면 배치 영역이 batch.size 만큼 쌓이지 않더라도 레코드를 바로 브로커로 전송하게 됨. 그러므로 linger.ms의 값을 적절하게 설정하여 브로커로 레코드를 보내는 요청 수를 줄여 브로커의 부하를 줄여주는 것이 좋음.
- kafka-client 3.1.2의 producer는 default partitioner 전략으로 레코드에 키와 파티션을 지정해주지 않으면 accumulator 내부에 토픽의 파티션 배치 영역이 batch.size 만큼 가득 차거나 linger.ms에 설정된 시간이 돌아오기 전 까지 토픽의 고정된 파티션으로 레코드를 전송하며 레코드가 브로커로 전송이 되면 토픽의 고정된 파티션이 다른 파티션으로 바뀌게 됨. **[KIP-480 참고](https://cwiki.apache.org/confluence/display/KAFKA/KIP-480%3A+Sticky+Partitioner)** 
- **[member producer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-producer/src/main/java/com/example/moduleproducer/common/config/MemberProducerConfiguration.java)**
- **[coupon producer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-producer/src/main/java/com/example/moduleproducer/common/config/CouponProducerConfiguration.java)**

**topic**</br>
- 각 토픽의 min.insync.replicas **(토픽 파티션의 리더가 프로듀서에게 ack 응답을 보내기 위해 확인해야 할 최소 리플리케이션의 수)** 값을 2로 설정하고 각 프로듀서의 acks 값을 -1(all)로 설정하여 카프카의 고가용성을 확보.</br>
- **카프카 브로커가 3대인 경우 acks=-1, min.insync.replicas=3 설정하였을 때 브로커 1대가 장애가 나는 순간 가용 가능한 브로커는 2대라서 리플리케이션 조건이 충족시킬 수 없기 때문에 브로커들은 더 이상 레코드를 받지 못하고 전체적인 장애로 발생하기 때문에 min.insync.replicas 값은 브로커 - 1 이하의 값으로 설정해야 함.**
![스크린샷 2023-11-25 오후 10 56 35](https://github.com/rbsks/kafka-practice/assets/67041069/6a36e43a-a410-4438-b99a-c6b8f39b9d77)</br>

**consumer**</br>
- 파티션과 컨슈머의 수를 동일하게 설정하여 데이터의 처리속도를 높임. 
- 컨슈머 커밋 전략에 대한 공부가 조금 더 필요할 듯.
- **[member consumer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-consumer/src/main/java/com/example/moduleconsumer/common/config/MemberConsumerConfiguration.java)**</br>
- **[coupon consumer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-consumer/src/main/java/com/example/moduleconsumer/common/config/CouponConsumerConfiguration.java)**
![스크린샷 2023-11-25 오후 11 02 59](https://github.com/rbsks/kafka-practice/assets/67041069/3eb98b8a-1dd7-49a1-b13f-bffa7206078e)

