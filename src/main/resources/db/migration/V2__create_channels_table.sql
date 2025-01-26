CREATE TABLE IF NOT EXISTS td_channels(
    id INT PRIMARY KEY AUTO_INCREMENT,
    channel_name VARCHAR(128),
    channel_type VARCHAR(128),
    ownerId INT,
    is_active INT DEFAULT 1
);