CREATE TYPE activity_type AS ENUM ('post', 'comment', 'comment_like', 'post_like', 'follow');

CREATE TYPE like_type AS ENUM ('comment', 'post');

CREATE TABLE members (
                         id BIGINT NOT NULL,
                         created_at TIMESTAMP NULL,
                         updated_at TIMESTAMP NULL,
                         email VARCHAR(255) NULL,
                         password VARCHAR(255) NULL,
                         name VARCHAR(50) NULL,
                         profile_image VARCHAR(255) NULL,
                         greetings VARCHAR(100) NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE refresh_tokens (
                                id BIGINT NOT NULL,
                                member_id BIGINT NOT NULL,
                                created_at TIMESTAMP NULL,
                                token VARCHAR(255) NULL,
                                expiry_date TIMESTAMP NULL,
                                PRIMARY KEY (id)
);

CREATE TABLE feeds (
                       id BIGINT NOT NULL,
                       member_id BIGINT NOT NULL,
                       activity_id BIGINT NOT NULL,
                       created_at TIMESTAMP NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE activities (
                            id BIGINT NOT NULL,
                            member_id BIGINT NOT NULL,
                            target_id BIGINT NULL,
                            type activity_type,
                            created_at TIMESTAMP NULL,
                            PRIMARY KEY (id)
);

CREATE TABLE comments (
                          id BIGINT NOT NULL,
                          member_id BIGINT NOT NULL,
                          post_id BIGINT NOT NULL,
                          created_at TIMESTAMP NULL,
                          updated_at TIMESTAMP NULL,
                          content VARCHAR(255) NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE likes (
                       id BIGINT NOT NULL,
                       member_id BIGINT NOT NULL,
                       target_id BIGINT NULL,
                       type like_type,
                       created_at TIMESTAMP NULL,
                       updated_at TIMESTAMP NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE posts (
                       id BIGINT NOT NULL,
                       member_id BIGINT NOT NULL,
                       created_at TIMESTAMP NULL,
                       updated_at TIMESTAMP NULL,
                       title VARCHAR(255) NULL,
                       content TEXT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE follows (
                         id BIGINT NOT NULL,
                         from_member_id BIGINT NOT NULL,
                         to_member_id BIGINT NOT NULL,
                         created_at TIMESTAMP NULL,
                         PRIMARY KEY (id)
);