--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    login_type VARCHAR(8) NOT NULL,
    login_value VARCHAR(256) NOT NULL,
    groups INTEGER DEFAULT 0 NOT NULL
);

--
-- Dumping data for table `users`
--
INSERT INTO users VALUES
    (1, 'matteo.brunello@edu.unito.it', 'PASSWORD', 'pwd123', 2),
    (2, 'giuseppe.eletto@edu.unito.it', 'PASSWORD', 'pwd123', 3),
    (3, 'edoardo.chiavazza@edu.unito.it', 'FACEBOOK', 'token123', 1);

--
-- Table structure for table `groups`
--
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    title VARCHAR(128) UNIQUE NOT NULL,
    description TEXT
);

--
-- Dumping data for table `groups`
--
INSERT INTO groups VALUES
    (1, 'Tornado Racers', 'These Tornado Racers are a new twist on the classic game of capture the cheese. You must capture all the cheese before your opponent does.'),
    (2, 'Electric Eels', 'Electric Eels are a small, slimy fish that are found in the water around the islands of Hawaii. They are preyed upon by sharks and large fish, so they have evolved a unique set of defense mechanisms.'),
    (3, 'New York Racoons', 'These fascinating racoons are the most frequently seen mammal in Central Park. The New York racoon has a long history of human interaction. Learn more about them, their natural history, and their care.'),
    (4, 'Red Panthers', 'The Red Panthers are the official football team of South Africa. Over the years, they have won many international football tournaments and are considered to be the best South African team.'),
    (5, 'Awesome Fighters', 'A collection of engaging and useful information on fighters, swordplay, and martial arts.'),
    (6, 'Retro Kings', 'Retro Kings is a new way to remember your past. Share your memories of the past with friends and family.');

--
-- Table structure for table `meetings`
--
CREATE TABLE IF NOT EXISTS meetings (
    id SERIAL PRIMARY KEY,
    group_id INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    datetime BIGINT NOT NULL,
    type VARCHAR(32) NOT NULL,
    location VARCHAR(256) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

--
-- Dumping data for table `meetings`
--
INSERT INTO meetings VALUES
    (1, 1, 'Ali della libertà', 11, 'PHYSICAL', 'Torino'),
    (2, 2, 'A scuola con Paolo', 12, 'PHYSICAL', 'Torino'),
    (3, 2, 'Ulzio e le sue caprette', 14, 'ONLINE', 'Torino'),
    (4, 3, 'Giovani autistici', 16, 'ONLINE', 'Torino');

--
-- Table structure for table `members`
--
CREATE TABLE IF NOT EXISTS members (
    group_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_owner BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (group_id) REFERENCES groups(id),
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
CREATE TABLE IF NOT EXISTS attendees (
    meeting_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_host BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (meeting_id) REFERENCES meetings(id),
    PRIMARY KEY (user_id, meeting_id)
);

--
-- Dumping data for table `attendees`
--
INSERT INTO attendees VALUES
    (1, 1,  true),
    (1, 2, false),
    (1, 3, false),
    (2, 1, false),
    (2, 2, false),
    (2, 3,  true),
    (3, 1, false),
    (3, 2, false),
    (3, 3,  true);
