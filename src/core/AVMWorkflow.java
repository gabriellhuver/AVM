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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import objects.Settings;
import util.AfterEffectsUtil;
import util.JSONUtil;
import util.ScriptMaker;
import util.WebDriverTool;
import util.WriteLogFile;
import objects.YoutubeVideo;

/**
 *
 * @author gab00
 */
public class AVMWorkflow {

    public static List<String> logList = new ArrayList<>();
    public static Scanner scan;
    public static TwitchSearchUTIL searchUTIL;
    public static int filesToDownload = 7;

    public static void go() {
        try {
            loading();
            scan = new Scanner(System.in);
            scan.useLocale(Locale.US);
            log("[1] - Create Twitch clip compilation by game");
            log("[2] - Create Twitch clip compilation by page url");
            log("[3] - Create Twitch clip compilation by Clip link list");
            log("[4] - Open Youtube Video config file");
            log("[5] - Reload config");

            log("[0] - Exit");
            switch (scan.next()) {
                case "1":
                    createClipByGame();
                    break;
                case "2":
                    createClipByUrl();
                    break;
                case "3":
                    createClipByList();
                    break;
                case "4":
                    openYoutubeVideoConfigCreatorGUI();
                    break;
                case "5":
                    AVM.loadConfig();
                    go();
                    break;
                case "0":
                    System.exit(1);
                    break;
                default:
                    go();

            }

        } catch (Exception ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    private static void loading() throws InterruptedException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            WebDriverTool.killChromeProcess();
            System.out.println("*****   TWITCH ROBOT  AUTO UPLOAD     ****");
            log("Welcome to Auto video maker! enjoy");
            searchUTIL = new TwitchSearchUTIL();
            Thread.sleep(200);
            createTempPath();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void createClipByGame() {
        String game = JOptionPane.showInputDialog("Game ?");
        if (!game.equals("")) {
            String urlGame = "https://www.twitch.tv/directory/game/" + game.replace(" ", "%20") + "/clips?range=24hr";
            //System.out.print("Clips per video: ");
            filesToDownload = settings.getFilesToDownload();

            if (filesToDownload > 0) {
                try {
                    List<String> listClipsByURL = searchUTIL.getListClipsByURL(urlGame, filesToDownload);
                    listClipsByURL.forEach((string) -> {
                        log("Link found " + string);
                    });

                    for (Iterator<String> it = listClipsByURL.iterator(); it.hasNext();) {
                        String string = it.next();
                        searchUTIL.downloadClip(string);
                    }
                    // Copy files
                    try {
                        log("Copy files init.");

                        AfterEffectsUtil.copyFilesToPath(AVM.tempDownloadPath, true);
                    } catch (Exception e) {
                        log("Error on copy files.");
                    } finally {
                        createProject();
                        AfterEffectsUtil.renderTemplate();
                        try {
                            avm.AVM.settings.setFinalVideoConvertedPath(avm.AVM.settings.getFinalVideoPath().replace("finalVideo", "convertedFinalVideo"));
                            String createTempBatEncodeFile = ScriptMaker.createTempBatEncodeFile(avm.AVM.settings.getFinalVideoPath(), avm.AVM.settings.getFinalVideoConvertedPath());
                            HandbrakeUtil.convertFile(createTempBatEncodeFile);
                        } catch (Exception e) {
                            log("Erro on script or convert file");
                        } finally {
                            try {
                                makeUpload();

                            } catch (Exception e) {
                                makeUpload();
                            }
                        }
                    }

                } catch (Exception e) {
                    log(e.getMessage());
                    createClipByGame();
                }

            } else {
                createClipByGame();
            }

        }
    }

    public static void makeUpload() {
        YoutubeVideo video;
        try {
            if (searchUTIL == null) {
                searchUTIL = new TwitchSearchUTIL();
            }
            video = JSONUtil.getVideoConfig(avm.AVM.settings.getVideoJsonFile());
            searchUTIL.doUpload(video);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException ex) {
            Logger.getLogger(AVMWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ifUploadError() {
        YoutubeVideo video;
        log("Not found! " + avm.AVM.settings.getVideoJsonFile());
        System.out.println("Searching dor Youtube config JSON files on -> " + settings.getMainVideoPath());
        List<String> files = JSONUtil.searchFilesFrom(settings.getMainVideoPath(), ".json");
        System.out.println("Select METADA Json file: ");
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

    private static void createClipByUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createClipByList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void openYoutubeVideoConfigCreatorGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void createTempPath() {
        try {
            if (avm.AVM.tempDownloadPath == null) {
                File f = new File(avm.AVM.settings.getMainVideoPath() + "//videos//VideoDownloadTempFolder" + new Date().getTime());
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
        try {
            deleteOldProjectFile();
            AfterEffectsUtil.createProjectByScript();
        } catch (IOException ex) {
            log(ex.getMessage());
        } catch (InterruptedException ex) {
            log(ex.getMessage());
        } finally {
            File f = new File(avm.AVM.settings.getAfterEffectsProjectFile());
            if (!f.exists()) {
                createProject();
            } else {
                log("Project created !");
            }
        }
    }

    private static void deleteOldProjectFile() {
        try {
            File f = new File(avm.AVM.settings.getAfterEffectsProjectFile());
            f.delete();
        } catch (Exception e) {
            log("Erro on delete old project!");
        }
    }

}
