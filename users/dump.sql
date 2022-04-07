--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(128) UNIQUE NOT NULL,
    auth_type  VARCHAR(8)          NOT NULL,
    auth_value VARCHAR(256)        NOT NULL
);

--
-- Dumping data for table `users`
--
INSERT INTO users
VALUES (1, 'matteo.brunello@edu.unito.it', 'PASSWORD', 'pwd123'),
       (2, 'giuseppe.eletto@edu.unito.it', 'PASSWORD', 'pwd123'),
       (3, 'edoardo.chiavazza@edu.unito.it', 'FACEBOOK', 'token123');

--
-- Table structure for table `members`
--
CREATE TABLE IF NOT EXISTS members
(
    group_id INTEGER               NOT NULL,
    user_id  INTEGER               NOT NULL,
    is_owner BOOLEAN DEFAULT false NOT NULL,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

--
-- Dumping data for table `members`
--
INSERT INTO members
VALUES (4, 1, true),
       (3, 1, true),
       (5, 2, true),
       (1, 2, true),
       (6, 2, true),
       (2, 3, true),
       (1, 1, false),
       (3, 2, false),
       (5, 3, false),
       (2, 1, false),
       (4, 2, false),
       (3, 3, false),
       (6, 1, false),
       (2, 2, false),
       (6, 3, false),
       (5, 1, false),
       (4, 3, false);

--
-- Table structure for table `attendees`
--
CREATE TABLE IF NOT EXISTS attendees
(
    meeting_id INTEGER               NOT NULL,
    user_id    INTEGER               NOT NULL,
    is_host    BOOLEAN DEFAULT false NOT NULL,
    PRIMARY KEY (user_id, meeting_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
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
