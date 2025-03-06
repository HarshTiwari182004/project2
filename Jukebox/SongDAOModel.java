package Jukebox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Jukebox.JDBC.getConnection;

public class SongDAOModel
{
    List<SongModel> listofsongs ; // arraylist for songfetch

    // fetch all the songs from the song table
    public List<SongModel> getAllSongs() throws RuntimeException {
        List<SongModel> songs = new ArrayList<>();
        String query = "SELECT SongID, title, artist, album, genre, duration, filePath FROM Songs";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                SongModel song = new SongModel();
                song.setSongID(rs.getInt("SongID"));
                song.setTitle(rs.getString("title"));
                song.setArtist(rs.getString("artist"));
                song.setAlbum(rs.getString("album"));
                song.setGenre(rs.getString("genre"));
                song.setDuration(rs.getLong("duration"));
                song.setFilePath(rs.getString("filePath"));
                songs.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }



    // Method to search songs based on category and search term

    public List<SongModel> searchSongs(List<SongModel> songList, String searchTerm) {
        List<SongModel> matchedSongs = new ArrayList<>();

        if (songList == null || songList.isEmpty()) {
            System.out.println("No songs available to search.");
            return matchedSongs;
        }

        for (SongModel song : songList) {
            if (song.getTitle().toLowerCase().contains(searchTerm.toLowerCase())
                    || song.getArtist().toLowerCase().contains(searchTerm.toLowerCase())
                    || song.getAlbum().toLowerCase().contains(searchTerm.toLowerCase())
                    || song.getGenre().toLowerCase().contains(searchTerm.toLowerCase())) {
                matchedSongs.add(song);
            }
        }

        if (matchedSongs.isEmpty()) {
            System.out.println("No songs found matching the search term: " + searchTerm);
        }

        return matchedSongs;
    }



    // Method to display songs in tabular format
    public static void displaySongs(List<SongModel> viewsongs)
    {
        // Display songs in tabular format
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-20s | %-15s | %-15s | %-10s |%n",
                "ID", "Title", "Artist", "Genre", "Album", "Duration");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (SongModel song : viewsongs)
        {
            System.out.printf("| %-5d | %-30s | %-20s | %-15s | %-15s | %-10s |%n",
                    song.getSongID(), song.getTitle(), song.getArtist(), song.getGenre(),
                    song.getAlbum(), song.getDuration());
        }
        System.out.println("---------------------------------------------------------------------------------------------");




    }


    public SongModel getSongByID(int songID) {
        SongModel song = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM Songs WHERE SongID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, songID);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {

                    song = new SongModel();
                    song.setSongID(resultSet.getInt("SongID"));
                    song.setTitle(resultSet.getString("Title"));
                    song.setArtist(resultSet.getString("Artist"));
                    song.setAlbum(resultSet.getString("Album"));
                    song.setGenre(resultSet.getString("Genre"));
                    song.setFilePath(resultSet.getString("FilePath")); // Assuming this is a column for the file location
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;  // Returns null if no song is found with the given SongID
    }

}
