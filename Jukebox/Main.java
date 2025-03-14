package Jukebox;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SongDAOModel songDAO = new SongDAOModel();  // Ensure SongDAO is initialized
        PlaylistDAOModel playlistDAO = new PlaylistDAOModel();
        AudioPlayerModel audioPlayer = new AudioPlayerModel();

        while (true) {
            System.out.println("\n---- Jukebox Menu ----");
            System.out.println("1. View All Songs");
            System.out.println("2. Search Songs");
            System.out.println("3. Create a New Playlist");
            System.out.println("4. Add Song to Existing Playlist");
            System.out.println("6. View Songs in a Playlist");
            System.out.println("7. Delete Playlist");
            System.out.println("8. Play Songs from a Playlist");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // View all songs
                    displayAllSongs(songDAO);
                    break;

                case 2: // Search songs
                    searchAndDisplaySongs(songDAO, scanner);
                    break;

                case 3: // Create a new playlist
                    createNewPlaylist(playlistDAO, scanner);
                    break;

                case 4: // Add song to an existing playlist
                    addSongToPlaylist(playlistDAO, songDAO, scanner);
                    break;

                case 5: // View all playlists
                    displayAllPlaylists(playlistDAO);
                    break;

                case 6: // View songs in a playlist
                    viewSongsInPlaylist(playlistDAO, songDAO, scanner);
                    break;

                case 7: // Delete a playlist
                    deletePlaylist(playlistDAO, scanner);
                    break;

                case 8: // Play songs from a playlist
                    playSongsFromPlaylist(playlistDAO, songDAO, audioPlayer, scanner);
                    break;

                case 9: // Exit
                    System.out.println("Exiting Jukebox. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Display all songs
    private static void displayAllSongs(SongDAOModel songDAO) {
        List<SongModel> songList = songDAO.getAllSongs();
        if (songList.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            songDAO.displaySongs(songList);
        }
    }

    // Search and display songs
    private static void searchAndDisplaySongs(SongDAOModel songDAO, Scanner scanner) {
        System.out.print("Enter the search term (Title, Artist, Album, or Genre): ");
        String searchTerm = scanner.nextLine();

        List<SongModel> songList = songDAO.getAllSongs();
        List<SongModel> searchResults = songDAO.searchSongs(songList, searchTerm);

        if (searchResults.isEmpty()) {
            System.out.println("No matching songs found.");
        } else {
            songDAO.displaySongs(searchResults);
        }
    }

    // Create a new playlist
    private static void createNewPlaylist(PlaylistDAOModel playlistDAO, Scanner scanner) {
        System.out.print("Enter the name for the new playlist: ");
        String playlistName = scanner.nextLine();
        int playlistID = playlistDAO.insertPlaylist(playlistName);

        if (playlistID != -1) {
            System.out.println("New playlist created with ID: " + playlistID);
        } else {
            System.out.println("Failed to create the playlist.");
        }
    }

    // Add song to an existing playlist
    private static void addSongToPlaylist(PlaylistDAOModel playlistDAO, SongDAOModel songDAO, Scanner scanner) {
        displayAllPlaylists(playlistDAO);

        System.out.print("Enter the ID of the playlist to add songs to: ");
        int playlistID = scanner.nextInt();

        List<SongModel> allSongs = songDAO.getAllSongs();
        songDAO.displaySongs(allSongs);

        System.out.print("Enter the ID of the song to add: ");
        int songID = scanner.nextInt();

        playlistDAO.insertSongIntoPlaylist(playlistID, songID);
        System.out.println("Song added to the playlist.");
    }

    // View songs in a playlist
    private static void viewSongsInPlaylist(PlaylistDAOModel playlistDAO, SongDAOModel songDAO, Scanner scanner) {
        System.out.print("Enter the ID of the playlist to view songs: ");
        int playlistID = scanner.nextInt();

        // Get songs in the playlist and display their details
        List<SongModel> songs = playlistDAO.getSongsInPlaylist(playlistID, songDAO);
        if (songs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
        } else {
            for (SongModel song : songs) {
                System.out.println("Song ID: " + song.getSongID() + ", Title: " + song.getTitle() + ", Artist: " + song.getArtist());
            }
        }
    }

    // Delete a playlist
    private static void deletePlaylist(PlaylistDAOModel playlistDAO, Scanner scanner) {
        displayAllPlaylists(playlistDAO);

        System.out.print("Enter the ID of the playlist to delete: ");
        int playlistID = scanner.nextInt();

        if (playlistDAO.deletePlaylist(playlistID)) {
            System.out.println("Playlist deleted successfully.");
        } else {
            System.out.println("Failed to delete playlist.");
        }
    }

    // Display all playlists
    private static void displayAllPlaylists(PlaylistDAOModel playlistDAO) {
        List<PlaylistModel> playlists = playlistDAO.getAllPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No playlists available.");
        } else {
            for (PlaylistModel playlist : playlists) {
                System.out.println("Playlist ID: " + playlist.getPlaylistID() + ", Name: " + playlist.getPlaylistName());
            }
        }
    }

    // Play songs from a playlist
    private static void playSongsFromPlaylist(PlaylistDAOModel playlistDAO, SongDAOModel songDAO, AudioPlayerModel audioPlayer, Scanner scanner) {
        displayAllPlaylists(playlistDAO);

        System.out.print("Enter the ID of the playlist you want to play: ");
        int playlistID = scanner.nextInt();

        List<SongModel> songs = playlistDAO.getSongsInPlaylist(playlistID, songDAO);

        if (songs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
            return;
        }

        for (SongModel song : songs) {
            audioPlayer.play(song.getFilePath());
            System.out.println("Played: " + song.getTitle());
        }


    }

}



