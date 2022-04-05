--
-- Table structure for table `users`
--
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    auth_type VARCHAR(8) NOT NULL,
    auth_value VARCHAR(256) NOT NULL
);

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
