/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static avm.AVM.settings;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import util.ConfigUtil;
import util.JSONUtil;
import objects.YoutubeVideo;

/**
 *
 * @author gab00
 */
public class AVMWorkflowTest {

    public AVMWorkflowTest() {
    }

    @Test
    public void tt() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
// String localPath = "C://temp//settings.json";
        //System.out.println(localPath.split("/")[localPath.split("/").length-1]);;
        Thread.sleep(new Random().nextInt(9999));
        System.out.println((System.currentTimeMillis() - currentTimeMillis));
    }
    
    //@Test
    public void testSomeMethod() {
        try {
            settings = ConfigUtil.getSettings("C://temp//settings.json");
            YoutubeVideo video = JSONUtil.getVideoConfig(avm.AVM.settings.getVideoJsonFile());
            System.out.println(video.toString());
            
            AVMWorkflow.makeUpload();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AVMWorkflowTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //@Test
    public void tst(){
        try {
            YoutubeVideo video = new YoutubeVideo();
            
            List<String> links = new ArrayList<>();
            List<String> tags = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            video.setLinkClips(links);
            video.setDescription(desc);
            video.setTags(tags);
            
            video.setYoutubeVideoTittle("Twitch compilation - #01");
            
            video.setStatus("PRIVATE");
            
            video.setProjectFile("output.mov");
            //video.setConvertedFile();
            JSONUtil.saveConfig("C://Temp//videos.json", video);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AVMWorkflowTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
