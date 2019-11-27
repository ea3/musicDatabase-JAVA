import dev.ea3.model.Artist;
import dev.ea3.model.Datasource;
import dev.ea3.model.SongArtist;
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open datasource");
            return;
        }

        List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_DESC);
        if(artists == null){
            System.out.println("No artists!");
            return;
        }

        for (Artist artist : artists){
            System.out.println("ID + " + artist.getId()+ ", Name = " + artist.getName());
        }

        List<String> albumsForArtist =
                datasource.queryAlbumsForArtist("Pink Floyd", Datasource.ORDER_BY_DESC );

        for(String album : albumsForArtist){
            System.out.println(album);
        }

        List<SongArtist> songArtists = datasource.queryArtistsForSong("Go Your Own Way", Datasource.ORDER_BY_ASC);
        if(songArtists == null){
            System.out.println("Could not find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists){
            System.out.println("Artist name = " + artist.getArtistName()+ " Album name= " + artist.getAlbumName() +
                    "Track =  " + artist.getTrack());
        }
        datasource.querySongsMetadata();
        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs is: " + count);

        datasource.createViewForSongArtists();

        songArtists = datasource.querySongInfoView("Go Your Own Way");
        if(songArtists.isEmpty()){
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for (SongArtist artist : songArtists){
            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() + " Album name = " +
                    artist.getAlbumName() +
                    " Track number = " + artist.getTrack());
        }
        datasource.close();
    }



}
