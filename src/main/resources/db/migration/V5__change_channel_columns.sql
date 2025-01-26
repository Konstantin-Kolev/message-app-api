ALTER TABLE td_channels
ALTER COLUMN channel_type INT;

ALTER TABLE td_channels
RENAME COLUMN ownerId to owner_id;