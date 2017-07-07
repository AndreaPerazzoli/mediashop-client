package model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andreaperazzoli on 07/07/17.
 */
public class Track {
    private String title;
    private Integer track_order;

    public String getTitle() {
        return title;
    }

    public Integer getTrack_order() {
        return track_order;
    }

    public Track(Map<String, Object> trackInfo){
        this.title = (String) trackInfo.get("title");
        this.track_order = ((Double)trackInfo.get("track_order")).intValue();

    }

    public static ArrayList<Track> getTracksBy(Integer productId) throws Exception{
        ArrayList<Track> tracksArrayList = new ArrayList<>();
        // get all tracks
        RestHandler handler = new RestHandler();
        //TODO: url get tracks by id
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("productId", productId.toString()));
        ArrayList<Map<String, Object>> tracks_info =handler.postRequest(UrlList.getTrackByProductId.toString(), params);




        for(Map<String, Object> track : tracks_info){
            tracksArrayList.add(new Track(track));
        }

        return tracksArrayList;
    }

    @Override
    public String toString() {
        return "[" + title + "," + track_order + "]";
    }
}
