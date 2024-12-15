# Short-Form Script Generator
## 프로젝트 설명  
이 프로젝트는 연구 논문의 초록을 기반으로 숏폼 대본을 자동으로 생성하는 서비스입니다.  
GPT-4를 활용하여 초록을 요약 및 각색한 스크립트를 생성하고, Python OpenCV를 통해 영상을 제작합니다.

---

## 목표  
1. 논문의 초록과 상세 정보를 저장  
2. 논문의 초록을 활용하여 관심을 끌만한 숏폼 대본 작성  
    - **OpenAI**  
    - **Spring AI**  
3. DB 관리  
    - MySQL  
    - 표준 ORM인 JPA 사용  
    - DB 최적화  

---

## 개발 환경 및 사용 기술  
**환경**  
- Java 17 이상  
- Spring Boot 3.3.4  

**기술**  
1. **데이터 관리**  
    - JPA  
    - MySQL  
2. **AI 모델 활용**  
    - OpenAI API (Spring AI 사용)  
3. **비동기 처리**  
    - Spring WebFlux  
4. **API 문서화**  
    - Swagger  

---

## 사용 시나리오  
1. **논문 데이터 가져오기**  
    - KCI Open API를 사용해 논문의 초록과 세부 정보를 가져옵니다.  

2. **비디오 스크립트 생성**  
    - Spring AI를 활용해 GPT-4 모델을 프롬프팅하여 흥미로운 숏폼 대본을 생성합니다.  

3. **비디오 생성**  
    - Python으로 작성된 동영상 생성 API에 생성한 대본을 전달합니다.  
    - 생성된 동영상의 URL을 데이터베이스에 저장합니다.  

---

## 비즈니스 구조
![image](https://github.com/user-attachments/assets/b1b80b24-bb6d-4aef-ac5d-7a114914d93b)


---
## API 문서
- [**API 문서**](https://github.com/YeongJae0114/Short-thesis/blob/main/API_DOCUMENTATION.md)
---

## 참고 자료  
- [OpenAI GPT-4 Documentation](https://openai.com/gpt-4)  
- [KCI Open API](https://www.kci.go.kr)  
- [OpenCV Documentation](https://docs.opencv.org)  

---
