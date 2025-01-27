CREATE TABLE IF NOT EXISTS tc_channel_member(
    channel_id INT,
    user_id INT,
    PRIMARY KEY (channel_id, user_id)
);

CREATE TABLE IF NOT EXISTS tc_channel_admin(
    channel_id INT,
    user_id INT,
    PRIMARY KEY (channel_id, user_id)
);