--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    login_type VARCHAR(8) NOT NULL,
    login_value VARCHAR(256) NOT NULL
);

--
-- Dumping data for table `users`
--
INSERT INTO users VALUES
    (1, 'matteo.brunello@edu.unito.it', 'PASSWORD', 'pwd123'),
    (2, 'giuseppe.eletto@edu.unito.it', 'PASSWORD', 'pwd123'),
    (3, 'edoardo.chiavazza@edu.unito.it', 'FACEBOOK', 'token123');
    
--
-- Table structure for table `members`
--
CREATE TABLE IF NOT EXISTS members (
    group_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_owner BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    PRIMARY KEY (user_id, group_id)
);

--
-- Dumping data for table `members`
--
INSERT INTO members VALUES
    (4, 1,  true),
    (3, 1,  true),
    (5, 2,  true),
    (1, 2,  true),
    (6, 2,  true),
    (2, 3,  true),
    (1, 1),
    (3, 2),
    (5, 3),
    (2, 1),
    (4, 2),
    (3, 3),
    (6, 1),
    (2, 2),
    (6, 3),
    (5, 1),
    (4, 3);

--
-- Table structure for table `attendees`
--
CREATE TABLE IF NOT EXISTS attendees (
    meeting_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_host BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    PRIMARY KEY (user_id, meeting_id)
);

--
-- Dumping data for table `attendees`
--
INSERT INTO attendees VALUES
    (1, 1,  true),
    (2, 3,  true),
    (3, 2,  true),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 2),
    (3, 1),
    (3, 2);

