CREATE TABLE songs (id INTEGER NOT NULL PRIMARY KEY, audioDBId INTEGER, name VARCHAR(50), artist integer, audioDBArtistId INTEGER, album integer, audioDBAlbumId INTEGER, duration INTEGER, nlisteners INTEGER, genre TEXT);
CREATE TABLE albums (id INTEGER NOT NULL PRIMARY KEY, audioDBId INTEGER, name TEXT NOT NULL, artist INTEGER, audioDBArtistId INTEGER, nSongs INTEGER);
CREATE TABLE artists (id INTEGER NOT NULL PRIMARY KEY, audioDBId INTEGER, nAlbums INTEGER, name TEXT NOT NULL, nSongs INTEGER, country TEXT);


INSERT INTO songs (id, name, artist, album, likes) values (1, "Song 1", 1, 1, 0);
INSERT INTO songs (id, name, artist, album, likes) values (2, "Song 2", 1, 2, 11);
INSERT INTO songs (id, name, artist, album, likes) values (3, "Song 3", 2, 3, 5);
INSERT INTO songs (id, name, artist, album, likes) values (4, "Song 4", 2, 3, 7);

INSERT INTO albums (id, name, artist, nSongs) values (1, "Album 1", 1, 10);
INSERT INTO albums (id, name, artist, nSongs) values (2, "Album 2", 1, 7);
INSERT INTO albums (id, name, artist, nSongs) values (3, "Album 3", 2, 9);

INSERT INTO artists (id, name, nAlbums, nSongs) values (1, "Artist 1", 2, 17);
INSERT INTO artists (id, name, nAlbums, nSongs) values (2, "Artist 2", 1, 9);

SELECT * FROM songs INNER JOIN artists ON songs.artist == artists.id INNER JOIN albums ON songs.album == albums.id;

SELECT songs.name, artists.name, albums.name FROM songs INNER JOIN artists ON songs.artist == artists.id INNER JOIN albums ON songs.album == albums.id;



DROP TABLE songs;
DROP TABLE albums;
DROP TABLE artists;