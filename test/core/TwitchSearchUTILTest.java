/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.YoutubeVideo;
import org.junit.Test;
import util.JSONUtil;

/**
 *
 * @author gab00
 */
public class TwitchSearchUTILTest {

    public TwitchSearchUTILTest() {
    }

    //@Test
    public void testSomeMethod() {

        try {
            TwitchSearchUTIL iL = new TwitchSearchUTIL();
            iL.downloadClip("https://www.twitch.tv/dizzy/clip/PricklyKnottyStingrayDxCat");
        } catch (InterruptedException ex) {
            Logger.getLogger(TwitchSearchUTILTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    String desc = "Twitch Clips, momentos mais engraçados das lives 2019, 2018 e mais\n"
            + "\n"
            + "📌 Caso você seja o dono de algum dos clipes e não queira que ele apareça no canal, por favor vai tomar no seu cu vlw\n"
            + "\n"
            + "🔔 Ative o sininho para receber as notificações de quando enviarmos os próximos vídeos!\n"
            + "\n"
            + "📣 Gostaria que seu clipe apareça no próximo vídeo? Me envie clips pelo nosso discord:\n"
            + "\n"
            + "\n"
            + "👉 Streamers do vídeo:\n"
            + "";

    @Test
    public void testSomeMethod2() throws FileNotFoundException {
        YoutubeVideo video = new YoutubeVideo();

        video.setClipsPerComposition(7);
        List<String> links = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        List<String> descL = new ArrayList<>();
        descL.add(desc);
        video.setLinkClips(links);
        video.setDescription(descL);
        video.setTags(tags);
        tags.add("Tag");
        links.add("https://www.twitch.tv/dizzy/clip/PricklyKnottyStingrayDxCat");
        video.setYoutubeVideoTittle("Twitch compilation - #01");
        video.setConvertedFile("E://videos//convertedFinalVideo.mp4");
        video.setStatus("PRIVATE");
        video.setRenderConfig("\"BestConfig\"");
        video.setIntroFile("intro.mov");
        video.setProjectFile("E://videos//finalVideo.mov");
        video.setFileName("E://videos//finalVideo.mov");
        JSONUtil.saveConfig("C://Temp//video.json", video);

    }
}
