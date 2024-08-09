CREATE TYPE activity_type AS ENUM ('post', 'comment', 'comment_like', 'post_like', 'follow');

CREATE TYPE like_type AS ENUM ('comment', 'post');
CREATE SEQUENCE members_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE refresh_tokens_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE feeds_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE activities_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE comments_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE likes_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE posts_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE follows_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE members (
                         id BIGINT NOT NULL DEFAULT nextval('members_id_seq') PRIMARY KEY,
                         created_at TIMESTAMP NULL,
                         updated_at TIMESTAMP NULL,
                         email VARCHAR(255) NULL,
                         password VARCHAR(255) NULL,
                         name VARCHAR(50) NULL,
                         profile_image VARCHAR(255) NULL,
                         greetings VARCHAR(100) NULL
);

CREATE TABLE refresh_tokens (
                                id BIGINT NOT NULL DEFAULT nextval('refresh_tokens_id_seq') PRIMARY KEY,
                                member_id BIGINT NOT NULL,
                                created_at TIMESTAMP NULL,
                                token VARCHAR(255) NULL,
                                expiry_date TIMESTAMP NULL
);

CREATE TABLE feeds (
                       id BIGINT NOT NULL DEFAULT nextval('feeds_id_seq') PRIMARY KEY,
                       member_id BIGINT NOT NULL,
                       activity_id BIGINT NOT NULL,
                       created_at TIMESTAMP NULL
);

CREATE TABLE activities (
                            id BIGINT NOT NULL DEFAULT nextval('activities_id_seq') PRIMARY KEY,
                            member_id BIGINT NOT NULL,
                            target_id BIGINT NULL,
                            type activity_type,
                            created_at TIMESTAMP NULL
);

CREATE TABLE comments (
                          id BIGINT NOT NULL DEFAULT nextval('comments_id_seq') PRIMARY KEY,
                          member_id BIGINT NOT NULL,
                          post_id BIGINT NOT NULL,
                          created_at TIMESTAMP NULL,
                          updated_at TIMESTAMP NULL,
                          content VARCHAR(255) NULL
);

CREATE TABLE likes (
                       id BIGINT NOT NULL DEFAULT nextval('likes_id_seq') PRIMARY KEY,
                       member_id BIGINT NOT NULL,
                       target_id BIGINT NULL,
                       type like_type,
                       created_at TIMESTAMP NULL,
                       updated_at TIMESTAMP NULL
);

CREATE TABLE posts (
                       id BIGINT NOT NULL DEFAULT nextval('posts_id_seq') PRIMARY KEY,
                       member_id BIGINT NOT NULL,
                       created_at TIMESTAMP NULL,
                       updated_at TIMESTAMP NULL,
                       title VARCHAR(255) NULL,
                       content TEXT NULL
);

CREATE TABLE follows (
                         id BIGINT NOT NULL DEFAULT nextval('follows_id_seq') PRIMARY KEY,
                         from_member_id BIGINT NOT NULL,
                         to_member_id BIGINT NOT NULL,
                         created_at TIMESTAMP NULL
);
