# Aqqle 
[![OS](https://img.shields.io/badge/macOS-Monterey-000000?style=flat&logo=apple)]()
[![Java](https://img.shields.io/badge/Java-18-007396?style=flat&logo=openjdk)]()
[![Python](https://img.shields.io/badge/Python-3.7.9-3776AB?style=flat&logo=python)]()
[![Elastic Stack version](https://img.shields.io/badge/Elasticsearch-8.8.1-00bfb3?style=flat&logo=elastic-stack)]()
[![Elastic Stack version](https://img.shields.io/badge/kibana-8.8.1-00bfb3?style=flat&logo=elastic-stack)]()
[![Elastic Stack version](https://img.shields.io/badge/logstash-8.8.1-00bfb3?style=flat&logo=elastic-stack)]()
[![TensorFlow](https://img.shields.io/badge/TensorFlow-2.14-FF6F00?style=flat&logo=tensorflow)]()  
[![OpenCV](https://img.shields.io/badge/OpenCV-4.5.0-5C3EE8?style=flat&logo=opencv)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql)]()
[![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?style=flat&logo=redis)]()
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.5-231F20?style=flat&logo=apache-kafka)]()
[![Anaconda](https://img.shields.io/badge/Anaconda-2023.07-44A833?style=flat&logo=anaconda)]() 
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-1.29.2-2496ED?style=flat&logo=docker)]()
## What is Aqqle?
텍스트기반의 포털사이트 개인프로젝트 
[아빠는개발자](https://father-lys.tistory.com/category/Aqqle)의 블로그의 내용을 구현  
([개발잡부 900gle](https://ldh-6019.tistory.com/)의 es7 버전에서 es8 로 신규개발)  

### Development Stack

```yaml
OS: macOS
Languages:
  - Java: "18"
  - Python: "3.7.9"
Frameworks & Libraries:
  - TensorFlow: "2.14"
  - OpenCV: "4.5.0"
Databases:
  - MySQL
  - Redis
Message Brokers:
  - Kafka
Elastic Stack:
  - Elasticsearch: "8.8.1"
  - Kibana: "8.8.1"
  - Logstash: "8.8.1"
Virtualization & Env Management:
  - Anaconda
  - Docker Compose
```

---

## - Architecture - [phase.1](https://father-lys.tistory.com/20)
<a href="https://father-lys.tistory.com/53" style="display: block; text-align: center;">
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F2jv52%2FbtsFRCYBS54%2FVihtdDGxZt889IlKghaUo0%2Fimg.png" 
       alt="phase1" 
       style="max-width: 100%; background: linear-gradient(to right, rgba(255, 0, 150, 0.6), rgba(0, 204, 255, 0.6)); box-shadow: 10px 10px 30px rgba(0, 0, 0, 0.4); transition: transform 0.3s ease; margin: 20px auto;">
</a>

## - Project Directory Hierarchy

📦 docker  
┣ 📂 elastic  
┃ ┣ 📂 elasticsearch  
┃ ┣ 📂 kibana  
┃ ┗ 📂 logstash  
┣ 📂 kafka  
┣ 📂 redis  
┗ 📂 mysql

📦 application  
┣ 📂 aqqle  
┃ ┣ 📂 api (검색용 API)  
┃ ┣ 📂 common (공통파일)  
┃ ┣ 📂 consumer (Kafka Consumer)  
┃ ┣ 📂 crawler (데이터 크롤러)  
┃ ┣ 📂 indexer (색인 배치)  
┃ ┣ 📂 extract (DB to JSON 파일 변환 배치)  
┃ ┣ 📂 libs (OpenCV Library)  
┃ ┣ 📂 manage (Admin API)  
┃ ┣ 📂 producer (Kafka Producer)  
┃ ┗ 📂 web (Aqqle Web Site)

📦 third_party  
┗ 📂 tf-embeddings  
┗ 📂 api (TensorFlow Text Embedding API)


## - Project discription

1. docker : Elastic stack 관련 DockerFile 및 플러그인 파일
* docker
    * elastic
        * [elasticsearch](https://ldh-6019.tistory.com/category/ElasticStack/Elasticsearch)
        * extensions
        * [kibana](https://ldh-6019.tistory.com/category/ElasticStack/Kibana)
        * logstash
    * [kafka](https://ldh-6019.tistory.com/category/Kafka)
    * [Redis](https://ldh-6019.tistory.com/category/Kafka)
    * [MySql](https://father-lys.tistory.com/70)

#### Elasticsearch pluin
>analysis-nori - nori 한글형태소분석 플러그인   
doo-plugin-8.8.1.zip - aqqle search plugin   
kr-danalyzer-8.8.1.zip - aqqle token filter    
payload-score-0.1.zip - payload score plugin
#### Elasticsearch dictionary
>{ES_HOME}/config/stopFilter.txt - 불용어 사전  
{ES_HOME}/config/synonymsFilter.txt - 동의어 사전  
{ES_HOME}/config/user_dictionary.txt - 사용자정의 사전


Usage
 ```
# docker-compose.yml 파일이 위한 경로로 이동 
$ cd ~/aqqle/docker/elastic    

# elasticsearch, kibana 빌드/실행   
$ docker compose up -d --build

# 실행중인 container 확인
$ dodcker ps -a
 
# 컨네이너 로그확인    
$docker logs <CONTAINER ID or NAME>

# 컨네이너 접속
$docker exec -id <NAME> /bin/bash

#container 정지/삭제  
$ docker compose stop
$ docker compose down

#image 확인/삭제
$ docker images
$ docker rmi <IMAGE_ID>
``` 

kafka Usage
```shell
docker compose -f kafka-full.yml up -d --build
```


1. java : aqqle 프로젝트
* java
    * aqqle : Project root
        * [api - 검색 API](https://father-lys.tistory.com/category/Java/API)
        * common - 공통파일
        * [crawler - 웹사이트의 정보를 크롤링]
          * [crawler - 쇼핑데이터](https://father-lys.tistory.com/22)
          * [crawler - 뉴스데이터](https://father-lys.tistory.com/22)
        * [extract - 크롤링 후 데이터 파일 생성](https://father-lys.tistory.com/category/Aqqle/EXTRACT)
        * [indexer - DB의 내용을 ES에 색인](https://father-lys.tistory.com/category/Aqqle/INDEXER)
        * [libs - OpenCV lib 파일](https://ldh-6019.tistory.com/category/OpneCV)
        * [manage - Admin 에서 사용될 API (크롤링키워드관리)](https://father-lys.tistory.com/category/Aqqle/MANAGE)
        * [producer - Kafka producer (카프카 데이터 전송)](https://ldh-6019.tistory.com/category/aqqle%20shopping/producer)
        * [consumer - Kafka consumer (카프카 데이터 소비)](https://ldh-6019.tistory.com/category/aqqle%20shopping/consumer)
        * [web - aqqle 웹사이트](https://ldh-6019.tistory.com/category/aqqle%20shopping/web)


1. third_party : tensorflow Text-embedding 모델을 사용하기 위한 API
   * third_party
     * tf-embedding
       * [app - Text embedding API](https://father-lys.tistory.com/category/Python/Text%20embeddings)

Usage
```
#aqqle 가상환경 실행
$ conda activate aqqle

#text embedding api 실행
$ python app/api.py
 ``` 
--- 
### API architecture
<a href="https://father-lys.tistory.com/160">
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbassxZ%2FbtsMB1dGamS%2FogvFCuc8mrcAIlvCKiVN10%2Fimg.png" 
       alt="phase1" 
       style="border-radius: 15px; box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.3); transition: transform 0.3s ease;">
</a>

### API cache strategy
<a href="https://father-lys.tistory.com/55">
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbtPUnZ%2FbtszlGfAoQk%2Fdz18kEBgpeAkTOOQiKHFS1%2Fimg.png" 
       alt="phase1" 
       style="border-radius: 15px; box-shadow: 5px 5px 20px rgba(0, 0, 0, 0.3); transition: transform 0.3s ease;">
</a>

---
### Environment Preparation
<details open>
  <summary>presetup</summary>

* [docker 설치](https://ldh-6019.tistory.com/10)
* [docker MySql설치](https://father-lys.tistory.com/70)
* [anaconda 설치](https://father-lys.tistory.com/136)
* [anaconda tensorflow 설치](https://ldh-6019.tistory.com/118?category=1043090)
* [docker redis 설치](https://father-lys.tistory.com/41)

</details> 
