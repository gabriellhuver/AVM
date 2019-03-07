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

    private String youtubeVideoTittle;

    private List<String> description;

    private List<String> tags;

    private String status;

    private String fileName;

    private String projectFile;

    private List<String> linkClips;

    private String convertedFile;

    private String backupFile;

    public String getBackupFile() {
        return backupFile;
    }

    public void setBackupFile(String backupFile) {
        this.backupFile = backupFile;
    }

    public void addLinksToDescription(String game) {
        //String desc = "Twitch Auto Upload example\\r\\nCreated By GH\\r\\n\\r\\n**********************************\\r\\n\\r\\nJava\\r\\nWebdriver Selenium\\r\\nAfter Effects CC 2018\\r\\n***********************************\\r\\nITS JUST A TEST!!!!!!\\r\\nList of social media profiles\\r\\nWebsite\\r\\nContact info (email, address, etc.)\\r\\nITS JUST A TEST!!!!!!\\r\\n***********************************\\r\\nITS JUST A TEST!!!!!!\\r\\nCredits";

        String desc = "Best of Twitch " + game + " Clips";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        String dateString = format.format(new Date());

        this.description = new ArrayList<>();
        for (String linkClip : linkClips) {
            description.add(linkClip);
        }
        this.description.add(dateString);
    }

    public String getConvertedFile() {
        return convertedFile;
    }

    public void setConvertedFile(String convertedFile) {
        this.convertedFile = convertedFile;
    }

    public String getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
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
        return "YoutubeVideo{" + "youtubeVideoTittle=" + youtubeVideoTittle + ", description=" + description + ", tags=" + tags + ", status=" + status + ", fileName=" + fileName + ", projectFile=" + projectFile + ", linkClips=" + linkClips + ", convertedFile=" + convertedFile + '}';
    }

}
