--
-- Table structure for table `meetings`
--
CREATE TABLE IF NOT EXISTS meetings
(
    id       SERIAL PRIMARY KEY,
    group_id INTEGER      NOT NULL,
    name     VARCHAR(128) NOT NULL,
    datetime BIGINT       NOT NULL,
    type     VARCHAR(8)   NOT NULL,
    location VARCHAR(64)  NOT NULL,
    UNIQUE KEY (group_id, name)
);

--
-- Dumping data for table `meetings`
--
INSERT INTO meetings
VALUES (1, 1, 'Ali della libertà', 11, 'PHYSICAL', 'Torino'),
       (2, 2, 'A scuola con Paolo', 12, 'PHYSICAL', 'Torino'),
       (3, 2, 'Ulzio e le sue caprette', 14, 'ONLINE', 'Torino'),
       (4, 3, 'Giovani autistici', 16, 'ONLINE', 'Torino');

--
-- Table structure for table `attendees`
--
CREATE TABLE IF NOT EXISTS attendees
(
    meeting_id INTEGER               NOT NULL,
    user_id    INTEGER               NOT NULL,
    is_host    BOOLEAN DEFAULT false NOT NULL,
    PRIMARY KEY (user_id, meeting_id),
    FOREIGN KEY (meeting_id) REFERENCES meetings (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

--
-- Dumping data for table `attendees`
--
INSERT INTO attendees
VALUES (1, 1, true),
       (2, 3, true),
       (3, 2, true),
       (1, 2, false),
       (1, 3, false),
       (2, 1, false),
       (2, 2, false),
       (3, 1, false),
       (3, 3, false);
