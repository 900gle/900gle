# 900gle shopping (phase2)
[![Elastic Stack version](https://img.shields.io/badge/Elasticsearch-7.15.1-00bfb3?style=flat&logo=elastic-stack)]()
[![Elastic Stack version](https://img.shields.io/badge/kibana-7.15.1-00bfb3?style=flat&logo=elastic-stack)]()
[![Elastic Stack version](https://img.shields.io/badge/logstash-7.15.1-00bfb3?style=flat&logo=elastic-stack)]()

## What is 900gle shopping?
백터의 유사도 검색을 활용한 쇼핑플랫폼 개인프로젝트  
[개발잡부](https://ldh-6019.tistory.com)의 블로그의 내용을 구현 

#### 개발환경
* macOS
* java 14   
* python 3.7.9   
* elasticsearch 7.15.1  
* kibana 7.15.1  
* logstash 7.15.1  
* tensorflow 2.14
* OpenCV4.5.0
* MySql
* anaconda 
* docker compose 

---

## - Architecture - [phase2](https://ldh-6019.tistory.com/132?category=1046444)
[![phase4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc5okLp%2FbtrEHDeKNX8%2FbuAEKahavjZhlkl5VvyafK%2Fimg.png
)](https://ldh-6019.tistory.com/132?category=1046444)

## - Project directory structure

> docker
> > elastic-stack 
>>> elasticsearch  
>kibana   
>logstash  
>
> > kafka

> java
> > doo
> > >api  
common  
crawler  
indexer  
libs  
manage  
web
>


> python
> >tf-embeddings
>>>api  


## - Project discription

1. docker : Elastic stack 관련 DockerFile 및 플러그인 파일 
* docker
    * elastic-stack
        * [elasticsearch](https://ldh-6019.tistory.com/category/ElasticStack/Elasticsearch)
        * extensions
        * [kibana](https://ldh-6019.tistory.com/category/ElasticStack/Kibana)
        * logstash
    * [kafka](https://ldh-6019.tistory.com/category/Kafka)

#### Elasticsearch pluin
>analysis-nori - nori 한글형태소분석 플러그인   
doo-plugin-7.15.1.zip - 900gle search plugin   
kr-danalyzer-7.15.1.zip - 900gle token filter    
payload-score-0.1.zip - payload score plugin 
#### Elasticsearch dictionary
>{ES_HOME}/config/stopFilter.txt - 불용어 사전  
{ES_HOME}/config/synonymsFilter.txt - 동의어 사전  
{ES_HOME}/config/user_dictionary.txt - 사용자정의 사전


 Usage     
 ```
# docker-compose.yml 파일이 위한 경로로 이동 
$ cd ~/900gle/docker/elastic-stack    

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


1. java : 900gle 프로젝트  
* java
    * doo : Project root        
        * [crawler - 검색 API](https://ldh-6019.tistory.com/category/900gle%20shopping/api) 
        * common - 공통파일        
        * [crawler - 웹사이트의 상품정보를 크롤링](https://ldh-6019.tistory.com/category/900gle%20shopping/crawler)    
        * extract - 크롤링 후 데이터 파일 생성
        * [indexer - DB의 내용을 ES에 색인](https://ldh-6019.tistory.com/category/900gle%20shopping/indexer) 
        * [libs - OpenCV lib 파일](https://ldh-6019.tistory.com/category/OpneCV)
        * [manage - Admin 에서 사용될 API (크롤링키워드관리)](https://ldh-6019.tistory.com/category/900gle%20shopping/manage)
        * [web - 900gle shopping 웹사이트](https://ldh-6019.tistory.com/category/900gle%20shopping/web)
     
 
 1. python : tensorflow Text-embedding 모델을 사용하기 위한 API 
 * python
     * tf-embedding
        * [app - Text embedding API](https://ldh-6019.tistory.com/185?category=1043090)
        
Usage     
```
#900gle 가상환경 실행
$ conda activate 900gle

#text embedding api 실행
$ python app/api.py
 ``` 
       
 ---
##Preparation
* [docker 설치](https://ldh-6019.tistory.com/10)
* [docker MySql설치](https://ldh-6019.tistory.com/11)
* [anaconda 설치](https://ldh-6019.tistory.com/117)
* [anaconda tensorflow 설치](https://ldh-6019.tistory.com/118?category=1043090)
