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
CREATE SEQUENCE codes_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE members
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('members_id_seq'),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    email         VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(50),
    profile_image VARCHAR(255),
    greetings     VARCHAR(100)
);

CREATE TABLE posts
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('posts_id_seq'),
    member_id  BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    title      VARCHAR(255),
    content    TEXT,
    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE comments
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('comments_id_seq'),
    member_id  BIGINT,
    post_id    BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    content    VARCHAR(255),
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE TABLE likes
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('likes_id_seq'),
    member_id  BIGINT,
    target_id  BIGINT,
    type       like_type,
    created_at TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE follows
(
    id             BIGINT PRIMARY KEY DEFAULT nextval('follows_id_seq'),
    from_member_id BIGINT,
    to_member_id   BIGINT,
    created_at     TIMESTAMP,
    FOREIGN KEY (from_member_id) REFERENCES members (id),
    FOREIGN KEY (to_member_id) REFERENCES members (id)
);

CREATE TABLE activities
(
    id                   BIGINT PRIMARY KEY DEFAULT nextval('activities_id_seq'),
    member_id            BIGINT,
    target_base_owner_id BIGINT,
    target_id            BIGINT,
    type                 activity_type,
    created_at           TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (target_base_owner_id) REFERENCES members (id)
);

CREATE TABLE feeds
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('feeds_id_seq'),
    member_id   BIGINT,
    activity_id BIGINT,
    created_at  TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (activity_id) REFERENCES activities (id)
);

CREATE TABLE refresh_tokens
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('refresh_tokens_id_seq'),
    member_id   BIGINT,
    created_at  TIMESTAMP,
    token       VARCHAR(255),
    expiry_date TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE codes
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('codes_id_seq'),
    code        VARCHAR(50),
    email       VARCHAR(255),
    authed      BOOLEAN,
    created_at  TIMESTAMP,
    expiry_date TIMESTAMP
);