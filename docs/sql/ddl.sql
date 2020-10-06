CREATE DATABASE `ENGKI`;
USE `ENGKI`;

SET NAMES utf8;
SET time_zone = '+09:00';

CREATE TABLE `parent`
(
    `id`                 bigint      NOT NULL,
    `name`               varchar(20) NOT NULL,
    `email`              varchar(50),
    `receive_email_flag` bool        NOT NULL DEFAULT FALSE,
    CONSTRAINT `parent_pk` PRIMARY KEY (`id`),
    CONSTRAINT `parent_ck_email_lower_case` CHECK ( email = LOWER(email) ),
    CONSTRAINT `parent_ck_receive_email_flag_true_needs_not_null_email` CHECK (
            (receive_email_flag is true and email is not null) or receive_email_flag is false )
);

CREATE TABLE `kid`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `parent_id` bigint      NOT NULL,
    `name`      varchar(20) NOT NULL,
    `birthday`  date        NOT NULL,
    `icon`      varchar(50) NOT NULL,
    `exp`       bigint      NOT NULL DEFAULT 0,
    CONSTRAINT `kid_pk` PRIMARY KEY (`id`),
    CONSTRAINT `kid_parent_id_fk` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `kid_emotion`
(
    `id`            bigint        NOT NULL AUTO_INCREMENT,
    `kid_id`        bigint        NOT NULL,
    `evaluate_time` timestamp     NOT NULL DEFAULT current_timestamp(),
    `angry`         decimal(5, 2) NOT NULL DEFAULT 0,
    `disgusting`    decimal(5, 2) NOT NULL DEFAULT 0,
    `fearful`       decimal(5, 2) NOT NULL DEFAULT 0,
    `happy`         decimal(5, 2) NOT NULL DEFAULT 0,
    `sad`           decimal(5, 2) NOT NULL DEFAULT 0,
    `surprising`    decimal(5, 2) NOT NULL DEFAULT 0,
    `neutral`       decimal(5, 2) NOT NULL DEFAULT 0,
    CONSTRAINT `kid_emotion_pk` PRIMARY KEY (`id`),
    CONSTRAINT `kid_emotion_kid_id_fk` FOREIGN KEY (`kid_id`) REFERENCES `kid` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `theme`
(
    `id`   bigint      NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT NULL,
    `icon` varchar(40) NOT NULL,
    CONSTRAINT `theme_pk` PRIMARY KEY (`id`)
);

CREATE TABLE `word`
(
    `id`       bigint      NOT NULL AUTO_INCREMENT,
    `theme_id` bigint      NOT NULL,
    `word`     varchar(20) NOT NULL,
    `word_kor` varchar(20) NOT NULL,
    CONSTRAINT `word_pk` PRIMARY KEY (`id`),
    CONSTRAINT `word_theme_id_fk` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`)
);

CREATE TABLE `kid_word`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `kid_id`       bigint NOT NULL,
    `word_id`      bigint NOT NULL,
    `studied_date` date   NOT NULL,
    CONSTRAINT `kid_word_pk` PRIMARY KEY (`id`),
    CONSTRAINT `kid_word_kid_id_fk` FOREIGN KEY (`kid_id`) REFERENCES `kid` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `kid_word_word_id_fk` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`),
    CONSTRAINT `kid_word_date_uq` UNIQUE (`kid_id`, `word_id`, `studied_date`)
);

CREATE TABLE `image`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `file_path` varchar(40) NOT NULL,
    CONSTRAINT `image_pk` PRIMARY KEY (`id`)
);

CREATE TABLE `image_word`
(
    `image_id` bigint       NOT NULL,
    `word_id`  bigint       NOT NULL,
    `boundary` varchar(100) NOT NULL,
    CONSTRAINT `image_word_pk` PRIMARY KEY (`image_id`, `word_id`),
    CONSTRAINT `image_word_image_id_fk` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
    CONSTRAINT `image_word_word_id_fk` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`)
);

CREATE TABLE `image_caption`
(
    `image_id`    bigint       NOT NULL,
    `word_id`     bigint       NOT NULL,
    `caption`     varchar(100) NOT NULL,
    `caption_kor` varchar(100) NOT NULL,
    CONSTRAINT `image_caption_pk` PRIMARY KEY (`image_id`, `word_id`),
    CONSTRAINT `image_caption_image_id_fk` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
    CONSTRAINT `image_caption_word_id_fk` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`)
);

CREATE TABLE `custom_image`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `file_path` varchar(40) NOT NULL,
    `parent_id` bigint      NOT NULL,
    CONSTRAINT `custom_image_pk` PRIMARY KEY (`id`),
    CONSTRAINT `custom_image_parent_id_fk` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `custom_image_word`
(
    `image_id` bigint       NOT NULL,
    `word`     varchar(20)  NOT NULL,
    `boundary` varchar(100) NOT NULL,
    CONSTRAINT `custom_image_word_pk` PRIMARY KEY (`image_id`, `word`),
    CONSTRAINT `custom_image_word_custom_image_id_fk` FOREIGN KEY (`image_id`) REFERENCES `custom_image` (`id`)
);

CREATE TABLE `custom_image_caption`
(
    `image_id`    bigint       NOT NULL,
    `word`        varchar(20)  NOT NULL,
    `caption`     varchar(100) NOT NULL,
    `word_kor`    varchar(20)  NOT NULL,
    `caption_kor` varchar(100) NOT NULL,
    CONSTRAINT `custom_image_caption_pk` PRIMARY KEY (`image_id`, `word`),
    CONSTRAINT `custom_image_caption_custom_image_id_fk` FOREIGN KEY (`image_id`) REFERENCES `custom_image` (`id`)

);