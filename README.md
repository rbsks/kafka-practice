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
- module-consumer
- module-producer
- module-core
  -  module-jpa
  -  module-redis
  
module-core에서 모든 의존성을 가져가는 경우 모듈이 너무 거대해지고 코어 모듈을 사용하는 모듈에서 필요 없는 의존성과 코드를 가져가는 것을 방지하기 위해서 위와 같은 멀티 모듈 구조를 설계함.</br></br>

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
- **카프카 클라이언트 프로듀서의 동작 방식을 설명해야 함.**</br>
- **[member producer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-producer/src/main/java/com/example/moduleproducer/common/config/MemberProducerConfiguration.java)**</br>
- **[coupon producer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-producer/src/main/java/com/example/moduleproducer/common/config/CouponProducerConfiguration.java)**</br>

**topic**</br>
- 각 토픽의 min.insync.replicas **(토픽 파티션의 리더가 프로듀서에게 ack 응답을 보내기 위해 확인해야 할 최소 리플리케이션의 수)** 값을 2로 설정하고 각 프로듀서의 acks 값을 -1(all)로 설정하여 카프카의 고가용성을 확보하였습니다.</br>
- **카프카 브로커가 3대인 경우 acks=-1, min.insync.replicas=3 설정하였을 때 브로커 1대가 장애가 나는 순간 가용 가능한 브로커는 2대라서 리플리케이션 조건이 충족시킬 수 없기 때문에 브로커들은 더 이상 메시지를 받지 못하고 전체적인 장애로 발생하기 때문에 min.insync.replicas 값은 브로커 - 1 이하의 값으로 설정해 주어야 합니다.**
![스크린샷 2023-11-25 오후 10 56 35](https://github.com/rbsks/kafka-practice/assets/67041069/6a36e43a-a410-4438-b99a-c6b8f39b9d77)</br>

**consumer**</br>
- 파티션과 컨슈머의 수를 동일하게 설정하여 데이터의 처리속도를 높임. 
- 컨슈머 커밋 전략에 대한 공부가 조금 더 필요할 듯
- **[member consumer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-consumer/src/main/java/com/example/moduleconsumer/common/config/MemberConsumerConfiguration.java)**</br>
- **[coupon consumer configuration code](https://github.com/rbsks/kafka-practice/blob/main/module-consumer/src/main/java/com/example/moduleconsumer/common/config/CouponConsumerConfiguration.java)**
![스크린샷 2023-11-25 오후 11 02 59](https://github.com/rbsks/kafka-practice/assets/67041069/3eb98b8a-1dd7-49a1-b13f-bffa7206078e)

