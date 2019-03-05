/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gab00
 */
public class YoutubeVideo {

    private String fileName;
    private List<String> description;
    private String youtubeVideoTittle;
    private List<String> tags;
    private String status;
    private String renderConfig;
    private String projectFile;
    private int clipsPerComposition;
    private String introFile;
    private List<String> linkClips;
    private String convertedFile;
    
    public void addLinksToDescription() {
        //String desc = "Twitch Auto Upload example\\r\\nCreated By GH\\r\\n\\r\\n**********************************\\r\\n\\r\\nJava\\r\\nWebdriver Selenium\\r\\nAfter Effects CC 2018\\r\\n***********************************\\r\\nITS JUST A TEST!!!!!!\\r\\nList of social media profiles\\r\\nWebsite\\r\\nContact info (email, address, etc.)\\r\\nITS JUST A TEST!!!!!!\\r\\n***********************************\\r\\nITS JUST A TEST!!!!!!\\r\\nCredits";

        String desc = "Best of Twitch Tibia Clips DATE";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        String dateString = format.format(new Date());
        String link = "\n %link% ";
        this.description = new ArrayList<>();
        desc.replace("DATA", dateString);
        StringBuilder st = new StringBuilder(desc);
        for (String string : linkClips) {
            st.append(link.replace("%link%", string));
        }
        description.add(st.toString());

    }

    public String getConvertedFile() {
        return convertedFile;
    }

    public void setConvertedFile(String convertedFile) {
        this.convertedFile = convertedFile;
    }

    public String getRenderConfig() {
        return renderConfig;
    }

    public void setRenderConfig(String renderConfig) {
        this.renderConfig = renderConfig;
    }

    public String getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
    }

    public int getClipsPerComposition() {
        return clipsPerComposition;
    }

    public void setClipsPerComposition(int clipsPerComposition) {
        this.clipsPerComposition = clipsPerComposition;
    }

    public String getIntroFile() {
        return introFile;
    }

    public void setIntroFile(String introFile) {
        this.introFile = introFile;
    }

    public List<String> getLinkClips() {
        return linkClips;
    }

    public void setLinkClips(List<String> linkClips) {
        this.linkClips = linkClips;
    }

    public String getFileName() {
        return fileName;
    }

   

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getYoutubeVideoTittle() {
        return youtubeVideoTittle;
    }

    public void setYoutubeVideoTittle(String youtubeVideoTittle) {
        this.youtubeVideoTittle = youtubeVideoTittle;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "YoutubeVideo{" + "fileName=" + fileName + ", description=" + description + ", youtubeVideoTittle=" + youtubeVideoTittle + ", tags=" + tags + ", status=" + status + ", renderConfig=" + renderConfig + ", projectFile=" + projectFile + ", clipsPerComposition=" + clipsPerComposition + ", introFile=" + introFile + ", linkClips=" + linkClips + ", convertedFile=" + convertedFile + '}';
    }
    
    

}
