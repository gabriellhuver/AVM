/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gab00
 */
public class UploadList {

    private HashMap<String, YoutubeVideo> videos;

    public HashMap<String, YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(HashMap<String, YoutubeVideo> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "UploadList{" + "videos=" + videos + '}';
    }

}
