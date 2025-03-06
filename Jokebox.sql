use jukebox;
create TABLE Playlists (
    PlaylistID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) not null
);
CREATE TABLE Playlistsongs (
    PlaylistID INT,
    songID INT,  -- Make sure column name is 'songID' (consistent with the Songs table)
    FOREIGN KEY (PlaylistID) REFERENCES Playlists(PlaylistID) ON DELETE CASCADE,
    FOREIGN KEY (songID) REFERENCES Songs(SongID) ON DELETE CASCADE,
    PRIMARY KEY (PlaylistID, songID)
);
CREATE TABLE Songs (
    SongID INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    genre VARCHAR(255),
    duration BIGINT,
    filePath VARCHAR(255)
);
INSERT INTO Songs (title, artist, album, genre, duration, filePath)
VALUES
('Savage Love', 'Jawsh 685 & Jason Derulo', 'Savage Love (Laxed – Siren Beat)', 'Pop', 180, 'C:\\Users\\Harsh Tiwari\\Downloads\\savage love.wav'),
('Soul of Fire', 'Artist Name', 'Album Name', 'Rock', 220, 'C:\\Users\\Harsh Tiwari\\Downloads\\soul of fire.wav'),
('Love', 'Artist Name', 'Album Name', 'R&B', 210, 'C:\\Users\\Harsh Tiwari\\Downloads\\love.wav'),
('The Beat', 'Artist Name', 'Album Name', 'Hip-Hop', 180, 'C:\\Users\\Harsh Tiwari\\Downloads\\the beat.wav'),
('Journey', 'Artist Name', 'Album Name', 'Classical', 240, 'C:\\Users\\Harsh Tiwari\\Downloads\\journey.wav');

select * from songs;
select * from playlists;
select* from playlistsongs;
desc songs;
select * from artists;
Drop Table playlistsongs;
select * from albums;
set Foreign_key_checks = 0;