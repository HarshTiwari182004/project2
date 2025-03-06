package Jukebox;

public class PlaylistModel
{
    private int playlistID;
    private String playlistName;

    public PlaylistModel() {
    }

    public PlaylistModel(int playlistID, String playlistName) {
        this.playlistID = playlistID;
        this.playlistName = playlistName;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistID=" + playlistID +
                ", playlistName='" + playlistName + '\'' +
                '}';
    }
}

