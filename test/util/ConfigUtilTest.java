/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Settings;
import objects.YoutubeVideo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gab00
 */
public class ConfigUtilTest {

    public ConfigUtilTest() {
    }
    public static String MAIN_FOLDER = "C://temp";
    public static String VIDEO_MAIN_FOLDER = MAIN_FOLDER + "//Videos//";
    public static String MAIN_JSON_FILE = MAIN_FOLDER + "//video.json";

    public static String YOUTUBE_UPLOAD_URL = "https://www.youtube.com/upload";
    public static String TEMP_VIDEO_RENDERIZER_FOLDER = MAIN_FOLDER + "//TempVideoRenderizer";
    public static String ASSETS_FOLDER = MAIN_FOLDER + "//assets";

    public static String AE_RENDER_EXEC = "C:/Program Files/Adobe/Adobe After Effects CC 2018/Support Files/aerender.exe";
    public static String AFTER_FX_RENDER = "C:/Program Files/Adobe/Adobe After Effects CC 2018/Support Files/AfterFX.exe";
    public static String HANDBRAKE_EXEC = "C:\\temp\\HandBrakeCLI";

    public static String CHROME_DRIVER_EXEC = "c://driver//chromedriver.exe";
    public static String CHROME_USER_DATA = "C:\\driver\\userData";

    public static String FINAL_VIDEO_FILE = "E:/videos/finalVideo.mov";
    public static String AFTER_EFFECTS_TEMPLATE_FILE = "C:/temp/video.aep";

    //
    public static String AFTER_EFFECTS_PROJECT_CREATOR_SCRIPT = "C:/temp/scriptFinal7.jsx";
    public static String AFTER_EFFECTS_SCRIPT_EDIT = "C://temp//scriptToEdit.jsx";
    // Robot Settings
    private static String GAME = "Apex%20Legends";

    public static int FILES_TO_DOWNLOAD = 7;

    public static boolean SKIP_DOWNLOAD = false;
    public static boolean EXPERT_MODE = false;

    public static final String OutputAESettings = "\"BestOut\"";
    public static final String RenderAESetings = "\"BestConfig\"";
    public static final String CompositionAEName = "\"main\"";

    @Test
    public void testSomeMethod() {
        try {
            Settings settings = new Settings();

            settings.setChromeDriver("C://driver//chromedriver.exe");
            settings.setChromeUserData("C://driver//userData");
            settings.setMainVideoPath(MAIN_FOLDER);
            settings.setAeRenderExec(AE_RENDER_EXEC);
            settings.setAfterEffectsExec(AFTER_FX_RENDER);
            settings.setAfterEffectsProjectCreatorScriptFile(AFTER_EFFECTS_PROJECT_CREATOR_SCRIPT);
            settings.setAfterEffectsProjectFile(AFTER_EFFECTS_TEMPLATE_FILE);
            settings.setAssetsFolder(ASSETS_FOLDER);
            settings.setFinalVideoPath(FINAL_VIDEO_FILE);
            settings.setMainVideoPath(MAIN_FOLDER);

            settings.setVideoRenderFolder(TEMP_VIDEO_RENDERIZER_FOLDER);
            settings.setVideoJsonFile(MAIN_JSON_FILE);
            settings.setYoutubeUploadUrl("https://www.youtube.com/upload");
            settings.setFinalVideoConvertedPath("E://videos//convertedFinalVideo.mov");
            settings.setHandBrakeExec(HANDBRAKE_EXEC);

            settings.setCompositionAEName(CompositionAEName);
            settings.setAeRenderRenderSettings(RenderAESetings);
            settings.setAeRenderOutputSettings(OutputAESettings);
            settings.setFilesToDownload(7);
            ConfigUtil.saveSettings("C://temp//settings.json", settings);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigUtilTest.class.getName()).log(Level.SEVERE, null, ex);
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
        video.setConvertedFile("E://videos//convertedFinalVideo.mov");
        video.setStatus("PRIVATE");
        video.setRenderConfig("\"BestConfig\"");
        video.setIntroFile("intro.mov");
        video.setProjectFile("E://videos//finalVideo.mov");
        video.setFileName("E://videos//finalVideo.mov");
        JSONUtil.saveConfig("C://Temp//video.json", video);

    }
}
