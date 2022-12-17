Lab 7: SQLite.

Due: Wednesday 12/8, Start of class.

In this lab, you'll get some practice interfacing with SQLite.

To submit: Please check this lab, including the updated music.db, back into your GitHib repo by the due date.

The lab will be graded according to the specifications grading method described in the syllabus.

Specifically:

A completed, on-time lab is worth 2 points.

A lab that is late or incomplete is worth 1 point.

A lab that is missing is worth 0 points.


Step 1: Download and install SQLite from here: https://www.sqlite.org/index.html

Step 2: Download and install the JDBC from here: https://github.com/xerial/sqlite-jdbc

Step 3: Start up the SQLite command-line shell with the music.db file.

Step 4: Look at the users table. 

Use select to:
- show all entries
- show everything for John and Paul
- Show George's password

Step 5: Use insert to add the following users, each with a unique ID:

user: luke, pw: tatooine. user: han, pw: falcon. user: leia, pw: princess.  

Step 6: Use update to change Paul's password.

Step 7: Use create to create an artists table with id, name, nAlbums, nSongs. Add at least three artists.

Step 8: Use create to create an albums table with id, name, artist, nSongs. Use the artist primary key as the foreign key. Add at least three albums.

Step 9: Use create to create a songs table with id, name, artist, album. Album and artist should use the
primary keys from the corresponding tables as foreign keys. Add at least five songs.

Step 10: I've provided our Song, Artist, Entity, Library and Album classes once again. If you want to use yours instead, please do!

I've added a toSQL method to Song that generates a string containing an insert statement that adds the object to the correct table. Add similar toSQL methods for Album and Artist.

Step 11. I've also added a fromSQL method to the Song class that takes as input a result set (the result of select * from table) and fills in the object's instance variables. Implement this for Album and Artist. 
You'll want to create unit tests to convince yourself that these work.

