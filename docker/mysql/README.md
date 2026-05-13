mysql


docker exec -it mysql bash

mysql -uroot -p --port 3306

:root

ALTER USER 'root'@'localhost' IDENTIFIED BY 'doo';

flush privileges;


mysql> show databases;
create database shop;

mysql> create user ldh; 

ALTER USER 'ldh'@'%' IDENTIFIED BY 'doo';

flush privileges;

GRANT ALL PRIVILEGES ON *.* TO ldh@'%';


```mysql

CREATE TABLE keywords (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `keyword` varchar(200) NOT NULL,
  `use_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  unique `keyword_index` (`keyword`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `aqqle_goods` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `keyword` varchar(200) DEFAULT NULL,
                               `brand` varchar(500) DEFAULT NULL,
                               `category` varchar(2000) DEFAULT NULL,
                               `category1` varchar(200) DEFAULT NULL,
                               `category2` varchar(200) DEFAULT NULL,
                               `category3` varchar(200) DEFAULT NULL,
                               `category4` varchar(200) DEFAULT NULL,
                               `category5` varchar(200) DEFAULT NULL,
                               `name` varchar(500) NOT NULL,
                               `price` bigint NOT NULL,
                               `weight` float NOT NULL,
                               `popular` float NOT NULL,
                               `image` varchar(400) DEFAULT NULL,
                               `feature_vector` text NOT NULL,
                               `type` varchar(50) NOT NULL,
                               `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `updated_time_index` (`updated_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `goods_text` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `keyword` varchar(200) DEFAULT NULL,
                              `brand` varchar(500) DEFAULT NULL,
                              `category` varchar(2000) DEFAULT NULL,
                              `category1` varchar(200) DEFAULT NULL,
                              `category2` varchar(200) DEFAULT NULL,
                              `category3` varchar(200) DEFAULT NULL,
                              `category4` varchar(200) DEFAULT NULL,
                              `category5` varchar(200) DEFAULT NULL,
                              `name` varchar(500) NOT NULL,
                              `price` bigint NOT NULL, -- 가격
                              `weight` float NOT NULL,   -- 가중치
                              `popular` float NOT NULL,  -- 인기도
                              `image` varchar(400) DEFAULT NULL,
                              `feature_vector` text NOT NULL, -- 특징점 백터
                              `type` varchar(50) NOT NULL,
                              `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              KEY `updated_time_index` (`updated_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


```