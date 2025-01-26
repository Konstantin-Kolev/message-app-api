CREATE TABLE IF NOT EXISTS tc_channel_member(
    channelId INT,
    userId INT,
    PRIMARY KEY (channelId, userId)
);

CREATE TABLE IF NOT EXISTS tc_channel_admin(
    channelId INT,
    userId INT,
    PRIMARY KEY (channelId, userId)
);