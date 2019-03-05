/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.List;

/**
 *
 * @author gab00
 */
public class UploadList {

    private String videoName;

    private List<YoutubeVideo> videos;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideo> videos) {
        this.videos = videos;
    }
    
    
    
}
