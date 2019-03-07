/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import avm.AVM;
import static avm.AVM.settings;
import util.HandbrakeUtil;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objects.LinkDatabase;
import util.AfterEffectsUtil;
import util.JSONUtil;
import util.ScriptMaker;
import util.WebDriverTool;
import objects.YoutubeVideo;

/**
 *
 * @author gab00
 */
public class AVMWorkflow {

    public static List<String> logList = new ArrayList<>();
    public static Scanner scan;
    public static TwitchSearchUTIL searchUTIL;
    public static YoutubeVideo video;
    public static String tempFileJSON;
    public static String afterEffectsScriptFile = "";

    public static void go() {
        try {
            loading();
            scan = new Scanner(System.in);
            scan.useLocale(Locale.US);
            printOptions();
            switch (scan.next()) {
                case "1":
                    log("Create Twitch clip compilation by game");
                    createClipByGame();
                    go();

                    break;
                case "2":
                    log("Create Twitch clip compilation by page url");
                    createClipByUrl();
                    go();

                    break;
                case "3":
                    log("Create Twitch clip compilation by Clip link list");
                    createClipByList();
                    go();

                    break;
                case "4":

                    openYoutubeVideoConfigCreatorGUI();
                    go();

                    break;
                case "5":
                    AVM.loadConfig();
                    go();
                    break;
                case "6":
                    AVM.openbrowser();

                    go();
                    break;
                case "7": {
                    createProject();
                    go();
                }
                break;
                case "8": {
                    try {
                        AfterEffectsUtil.renderTemplate();
                    } catch (IOException ex) {
                        Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        go();

                    }
                }
                break;
                case "9":
                    log("Creating batch file");
                    String createTempBatEncodeFile = ScriptMaker.createTempBatEncodeFile(avm.AVM.settings.getFinalVideoPath(), avm.AVM.settings.getFinalVideoConvertedPath());
                    log("Executing batch file");
                    HandbrakeUtil.convertFile(createTempBatEncodeFile);
                    go();

                    break;
                case "0":
                    System.exit(1);
                    break;
                default:
                    go();

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void printOptions() {
        System.out.println("[1] - Create Twitch clip compilation by game");
        System.out.println("[2] - Create Twitch clip compilation by page url");
        System.out.println("[3] - Create Twitch clip compilation by Clip link list");
        System.out.println("[4] - Open Youtube Video config file");
        System.out.println("[5] - Reload config");
        System.out.println("[6] - Open chromedriver");
        System.out.println("[7] - create project by " + settings.getAeScriptTemplate() + " template with " + settings.getFilesToDownload() + " clips");
        System.out.println("[8] - Render template with " + settings.getAfterEffectsProjectFile());
        System.out.println("[9] - Convert " + settings.getFinalVideoConvertedPath());
        System.out.println("[0] - Exit");
    }

    private static void loading() throws InterruptedException {
        WebDriverTool.killChromeProcess();
        log("Welcome to Auto video maker! enjoy");
        searchUTIL = new TwitchSearchUTIL();
        Thread.sleep(200);

    }

    public static void createClipByGame() {
        String game = JOptionPane.showInputDialog("Game ?");
        log("creating clips of " + game);
        if (!game.equals("")) {
            String urlGame = "https://www.twitch.tv/directory/game/" + game.replace(" ", "%20") + "/clips?range=24hr";
            log("Search url: " + urlGame);
            if (settings.getFilesToDownload() > 0) {
                createVideoByTwitchClipPageUrl(urlGame, game);

            } else {
                log("clips size 0");
            }

        }

    }

    public static void createVideoByTwitchClipPageUrl(String urlGame, String game) {
        try {
            log("[SEARCHING CLIPS]");
            List<String> clips = new ArrayList<>();
            do {
                clips = searchClips(urlGame);
            } while (clips.isEmpty());
            log(clips.size() + " clips found!");
            video.setLinkClips(clips);
            video.addLinksToDescription(game);
            log("video links added to description");
            JSONUtil.saveConfig(tempFileJSON, video);
            log("metada saved on temp file -> " + tempFileJSON);
            makeMovieByList(clips);

        } catch (IOException | InterruptedException e) {
            log(e.getMessage());
        }
    }

    public static void makeMovieByList(List<String> clips) throws IOException, InterruptedException {
        createTempPath();
        log("download temp file created " + AVM.tempDownloadPath);
        log("[DOWNLOAD FILES]");
        makeDownload(clips);
        // Copy files
        log("download folder -> " + AVM.tempDownloadPath);
        AfterEffectsUtil.copyFilesToPath(AVM.tempDownloadPath, true);
        log("creating project");
        createProject();
        log("rendering project");
        AfterEffectsUtil.renderTemplate();
        log("[HANDBRAKE WORKFLOW]");
        try {
            String replace = settings.getFinalVideoPath().replace("finalVideo", "convertedFinalVideo");
            settings.setFinalVideoConvertedPath(replace);
            log("finalVideoConvertPath setted to" + replace);
            log("Creating batch file");
            String createTempBatEncodeFile = ScriptMaker.createTempBatEncodeFile(settings.getFinalVideoPath(), avm.AVM.settings.getFinalVideoConvertedPath());
            log("Executing batch file");
            HandbrakeUtil.convertFile(createTempBatEncodeFile);
        } catch (InterruptedException e) {
            log("Erro on script or convert file");
        } finally {
            try {
                makeUpload();

            } catch (Exception e) {
                makeUpload();
            }
        }
    }

    private static void createClipByUrl() {
        try {
            String url = JOptionPane.showInputDialog("URL");
            createVideoByTwitchClipPageUrl(url, "");
        } catch (HeadlessException e) {
            log(e.getMessage());
        }

    }

    private static void createClipByList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private static void openYoutubeVideoConfigCreatorGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void makeDownload(List<String> clips) throws InterruptedException {
        try {
            for (String string : clips) {

                searchUTIL.downloadClip(string);
            }
        } catch (InterruptedException e) {
            log(e.getMessage());
        }
    }

    public static void makeUpload() {
        try {
            if (searchUTIL == null) {
                searchUTIL = new TwitchSearchUTIL();
            }
            saveLinksOnDatabase();
            searchUTIL.doUpload(video);
        } catch (InterruptedException | AWTException ex) {
            log(ex.getMessage());
        }
    }

    public static void ifUploadError() {
        log("Not found! " + settings.getVideoJsonFile());
        log("Searching dor Youtube config JSON files on -> " + settings.getMainVideoPath());
        List<String> files = JSONUtil.searchFilesFrom(settings.getMainVideoPath(), ".json");
        log("Select METADA Json file: ");
        int i = 0;
        for (String file : files) {
            System.out.println("[" + i + "] " + file);
            i++;
        }
        try {
            video = JSONUtil.getVideoConfig(files.get(scan.nextInt()));
        } catch (FileNotFoundException ex1) {
            makeUpload();
        }
    }

    public static String createTempAfterEffectsScript() {
        String fileName = settings.getAeScriptPath() + "\\" + UUID.randomUUID().toString().split("-")[0] + "Script.jsx";
        log("creating after effects script -> " + fileName);
        try {
            ScriptMaker.createScript(settings.getAeScriptTemplate(), settings.getFilesToDownload(), fileName);
            log("script created");
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            File f = new File(fileName);
            if (!f.exists()) {
                createTempAfterEffectsScript();
            }

        }
        return fileName;

    }

    public static void createTempPath() {
        try {
            if (avm.AVM.tempDownloadPath == null) {
                File f = new File(settings.getMainVideoPath() + "//videos//VideoDownloadTempFolder" + new Date().getTime());
                if (!f.exists()) {
                    f.mkdir();
                }
                AVM.tempDownloadPath = f.getAbsolutePath();
            }
        } catch (Exception e) {
            log("Erro on temp folder creation");
        }
    }

    public static void log(String log) {
        String str = new Date().toString() + ": " + log;
        logList.add(str);
        System.out.println(str);
    }

    private static void createProject() {
        Thread t = new Thread(() -> {
            AfterEffectsUtil.createProjectByScript(createTempAfterEffectsScript());
        });
        try {
            deleteOldProjectFile();
            t.start();
            log("waiting for project creation by after effects");
            Thread.sleep(18000);
            log("check video.aep file");
            File f = new File(settings.getAfterEffectsProjectFile());
            if (!f.exists()) {
                log("project creation erro");
                createProject();

            } else {
                log("Project created !");
            }
        } catch (InterruptedException ex) {
            log(ex.getMessage());
        }
    }

    private static void deleteOldProjectFile() {
        try {
            log("old project deleted from " + settings.getAfterEffectsProjectFile());
            File f = new File(settings.getAfterEffectsProjectFile());
            f.delete();
        } catch (Exception e) {
            log("Erro on delete old project!");
        }
    }

    private static void saveLinksOnDatabase() {

        try {
            log("Saving links on database!");
            LinkDatabase linkDatase = JSONUtil.getlinkDatase(settings.getVideoDatabseJSONFile());

            for (String link : video.getLinkClips()) {
                linkDatase.getLinks().add(link);
            }

            JSONUtil.saveLinkDatabase(settings.getVideoDatabseJSONFile(), linkDatase);
        } catch (FileNotFoundException ex) {
            log(ex.getMessage());
        } catch (IOException ex) {
            log(ex.getMessage());
        }

    }

    public static List<String> searchClips(String urlGame) throws FileNotFoundException {
        List<String> listClipsByURL = null;
        try {
            listClipsByURL = searchUTIL.getListClipsByURL(urlGame, settings.getFilesToDownload());
            listClipsByURL.forEach((String string) -> {
                log("Link found " + string);
            });
        } catch (FileNotFoundException e) {
            log("0 clips found try again");
        }
        return listClipsByURL;
    }

}
