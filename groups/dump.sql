--
-- Table structure for table `groups`
--
CREATE TABLE IF NOT EXISTS `groups`
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(128) UNIQUE NOT NULL,
    description TEXT DEFAULT NULL
);

--
-- Dumping data for table `groups`
--
INSERT INTO `groups`
VALUES (1, 'Tornado Racers',
        'These Tornado Racers are a new twist on the classic game of capture the cheese. You must capture all the cheese before your opponent does.'),
       (2, 'Electric Eels', 'Electric Eels are a small,
        slimy fish that are found in the water around the islands of Hawaii. They are preyed upon by sharks and large fish,
        so they have evolved a unique set of defense mechanisms.'),
       (3, 'New York Racoons',
        'These fascinating racoons are the most frequently seen mammal in Central Park. The New York racoon has a long history of human interaction. Learn more about them,
        their natural history, and their care.'),
       (4, 'Red Panthers', 'The Red Panthers are the official football team of South Africa. Over the years,
        they have won many international football tournaments and are considered to be the best South African team.'),
       (5, 'Awesome Fighters', 'A collection of engaging and useful information on fighters, swordplay, and martial
        arts.'),
       (6, 'Retro Kings',
        'Retro Kings is a new way to remember your past. Share your memories of the past with friends and family.');

--
-- Table structure for table `members`
--
CREATE TABLE IF NOT EXISTS members
(
    group_id INTEGER               NOT NULL,
    user_id  INTEGER               NOT NULL,
    is_owner BOOLEAN DEFAULT false NOT NULL,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (group_id) REFERENCES `groups` (id)
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
