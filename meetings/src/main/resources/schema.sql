--
-- Table structure for table `meetings`
--
CREATE TABLE IF NOT EXISTS meetings(
    id SERIAL PRIMARY KEY,
    group_id INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    datetime BIGINT NOT NULL,
    type VARCHAR(8) NOT NULL,
    location VARCHAR(64) NOT NULL
);

--
-- Table structure for table `attendees`
--
CREATE TABLE IF NOT EXISTS attendees(
    meeting_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    is_host BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (meeting_id) REFERENCES meetings(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    PRIMARY KEY (user_id, meeting_id)
);


