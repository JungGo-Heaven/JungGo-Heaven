## 👉🏼 [내일배움캠프] 최종 프로젝트 12조

### 🙋‍♀️ 실시간 채팅 기반 중고 거래 플랫폼
- **팀원 : 김형우, 권은서, 남윤재, 황제, 황보승**
- 기간 : 2025.04.01 - 2025.05.06



<br><br>

### 역할 분담
🐯 [khw00185](https://github.com/khw00185) <p>　　- WebSocket Stomp + Redis + Redis Pub/Sub으로 실시간 채팅 구현 <br>　　- RabbitMQ + Redis를 통해 경매 서비스 구현 <p><br>
🦅 [euuns](https://github.com/euuns) <p>　　- Spring Security + OAuth2.0 소셜 로그인 <br>　　- Toss Payments 연동 결제 시스템 <br>　　- jacoco를 통한 테스트 자동화 시각처리 <br>　　- github action을 이용한 EC2 배포 <p><br>
🐶 [yjn33](https://github.com/yjn33) <p>　　- 상품 기능 및 끌어올리기 구현 <br>　　- Random Forest 알고리즘을 이용한 사용자 기반 맞춤 추천 구현 <p><br>
👑 [JeisaNewbie](https://github.com/JeisaNewbie) <p>　　- 다양한 도메인에 사용 가능한 알림 서비스 구현 <br>　　- Elastic Search를 통한 검색 기능 향상 개선 <p><br>
🐹 [emily101304](https://github.com/emily101304) <p>　　- S3를 활용한 이미지 업로드 구현 <br>　　- CloudFront를 활용하여 CDN 연동 <br>　　- KakaoMap API를 활용한 GeoCoding <br>　　- MySQL GIS 기반 거리 계산 기능 구현 <p><br>


<br>

📑 진행 및 회의 기록 : [12조: 중고 천국(Jung-Go Heaven)](https://www.notion.so/teamsparta/12-Jung-Go-Heaven-1ce2dc3ef514814cbeebce87baafa56d) 노션 <br>
🔖 브로셔 페이지 : [12조: 중고 천국(Jung-Go Heaven)](https://www.notion.so/teamsparta/12-1e32dc3ef514807fbf2bce8559c3aaac) 노션


<br><br><br>


## 🛠 목차

1. [📚 STACKS](#-STACKS)
2. [👩🏻‍ API 명세](#-API-명세)
3. [🏗️ System Architecture](#-System-Architecture)
4. [🖼️ 와이어 프레임](#-와이어-프레임)
5. [🔧 기술적 의사결정](#-기술적-의사결정)
6. [💥 트러블슈팅](#-트러블슈팅)
<br>   

<br><br><br>

<div align=center> 

## 📚 STACKS

#### Backend, Analysis & Optimization
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white"> 
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white">
<img src="https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white">
<br>
<img src="https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/Elastic_Search-005571?style=for-the-badge&logo=elasticsearch&logoColor=white">
<img src="https://img.shields.io/badge/scikit_learn-F7931E?style=for-the-badge&logo=scikitlearn&logoColor=white">
<img src="https://img.shields.io/badge/pandas-150458?style=for-the-badge&logo=pandas&logoColor=white">
<br><br>

#### Other Tools
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
<img src="https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white">
<img src="https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=Kibana&logoColor=white">
<img src="https://img.shields.io/badge/apache_jmeter-D22128?style=for-the-badge&logo=apachejmeter&logoColor=white">
<br>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
<br><br>

#### Deployment & Distribution 
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white">
<img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazon&logoColor=white">
<img src="https://img.shields.io/badge/amazon_ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<br>
<img src="https://img.shields.io/badge/amazon_rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
<img src="https://img.shields.io/badge/amazon_s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
<img src="https://img.shields.io/badge/amazon_route53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">
<img src="https://img.shields.io/badge/amazon_loadbalancing-8C4FFF?style=for-the-badge&logo=awselasticloadbalancing&logoColor=white">
<br><br><br><br>

<div align=left> 

  
## 👩🏻‍ API 명세
<div align=center> 

-
  
<div align=left> 
  
## 🏗️ System Architecture

<div align=center> 

-

<div align=left> 
  
## 🖼️ 와이어 프레임

-

## 🔧 기술적 의사결정

-

## 💥 트러블슈팅

-
