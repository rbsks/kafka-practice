# 스프링 카프카를 활용한 회원 등록과 쿠폰 등록 
**학습 목적**
- 멀티 모듈의 이해
- 스프링 카프카를 이용한 카프카 프로듀서와 컨슈머의 이해

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
  
위와 같은 멀티 모듈 구조를 설계한 이유는 module-core에서 모든 의존성을 가져가는 경우 모듈이 너무 거대해지고 코어 모듈을 사용하는 모듈에서 필요 없는 의존성과 코드를 가져가는 것을 방지하기 위해서입니다.</br></br>

**module-consumer의 build.gradle**
<img width="1012" alt="스크린샷 2023-11-25 오후 10 30 37" src="https://github.com/TeamRocketDan/backend_server_chat/assets/67041069/784b9eba-5835-40e4-96fd-40a030ea2767"></br></br>
**module-producer의 build.gradle** 
<img width="1012" alt="스크린샷 2023-11-25 오후 10 30 10" src="https://github.com/TeamRocketDan/backend_server_chat/assets/67041069/afd198e2-70aa-47ac-bd91-03b60a3b16fb">
