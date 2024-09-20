## 프로젝트 특징
1. Docker를 활용한 로컬 개발 환경 구축
2. 모노리스 서비스의 마이크로 서비스화

## 개발 환경
- SpringBoot 3.XX
- Java 21
- JWT
- Docker / Docker Compose

## 아키텍처
![image](https://github.com/user-attachments/assets/6d838275-ce72-43a2-96e3-0f03580094c3)

## 구현 기능
- Resilience 4j의 Circuit Breaker, Retry 를 통한 회복탄력성 갖추기
- Feign Client 와 Kafka 를 이용한 마이크로서비스 간 통신
- Api Gateway를 통한 라우팅 및 인가 기능 구현
- 스케줄러를 통한 주문 및 배송 상태 관리
- Spring Security, JWT를 사용한 인증/인가
- Google SMTP를 사용한 이메일 인증
