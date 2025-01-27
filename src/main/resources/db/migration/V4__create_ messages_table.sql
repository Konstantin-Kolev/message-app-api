CREATE TABLE IF NOT EXISTS td_messages(
    id INT PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR(2000),
    timestamp VARCHAR(128),
    channel_id INT,
    sender_id INT,
    is_active INT DEFAULT 1
);