/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avm;

import core.AVMWorkflow;
import static core.AVMWorkflow.log;
import static core.AVMWorkflow.tempFileJSON;
import static core.AVMWorkflow.video;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import objects.LinkDatabase;
import objects.Settings;
import objects.UploadList;
import org.openqa.selenium.WebDriver;
import ui.MainFrame;
import util.ConfigUtil;
import util.JSONUtil;
import util.WebDriverTool;
import util.WriteLogFile;

/**
 *
 * @author gab00
 */
public class AVM {

    public static AVMWorkflow workflow;
    public static String jsonStg = "";
    public static String logPath = "";
    public static Settings settings;
    public static LinkDatabase databaseLink;
    public static String tempDownloadPath;
    public static UploadList uploadList;
    public static boolean ui;

    public static void main(String[] args) {
        try {
            
            log("Loading....");
            addExitHook();
            AVM.jsonStg = args[1];
            AVM.logPath = args[3];
            loadConfig();

        } catch (Exception e) {
            log(e.getMessage());
            log("Args Error, try -cfg x://tt//file.json -log x://log");

        } finally {
            if (settings != null) {
                AVMWorkflow.go();
            }
        }

    }

    public static void initUI(String[] args) throws IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException, ClassNotFoundException {
        if (args[4].equals("-ui")) {
            ui = true;
            SwingWorker<Void, Void> worker = new SwingWorkerUI();
            worker.execute();

        }
    }

    public static void loadConfig() {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(AVM.class.getName()).log(Level.SEVERE, null, ex);
            }

            log(Settings.loadingStr);
            log("----------------------------  Created by GH. ----------------------------");

            Thread.sleep(1666);

            settings = ConfigUtil.getSettings(jsonStg);
            
            
            
            databaseLink = JSONUtil.getlinkDatase(settings.getVideoDatabseJSONFile());

            log("Loading configs...");
            log("JSON Settings file: " + jsonStg);
            log("Loading temp folders...");

            AVMWorkflow.video = JSONUtil.getVideoConfig(settings.getVideoJsonFile());
            createTempJSONFile();
        } catch (FileNotFoundException | InterruptedException e) {
            log("Erro on load Settings!");
        }
    }

    private static void createTempJSONFile() {
        try {
            tempFileJSON = avm.AVM.settings.getMainVideoPath() + "//videos//JsonFiles//Video" + new Date().getTime() + ".json";
            JSONUtil.saveConfig(tempFileJSON, video);
            log(tempFileJSON);
            log(AVMWorkflow.video.toString());
            log("Json temp file saved on" + tempFileJSON);
        } catch (FileNotFoundException ex) {
            log(ex.getLocalizedMessage());
        }

    }

    public static void addExitHook() {
        Runtime.getRuntime().addShutdownHook(new ThreadWriteLog());
    }

    public static void openbrowser() {
        WebDriver driver = null;
        try {
            driver = WebDriverTool.setupDriver();

            driver.get("https://youtube.com");
            Scanner scan = new Scanner(System.in);
            log("Press key to continue...");
            scan.next();
        } catch (Exception e) {
            driver.close();
        } finally {

            driver.close();
            AVMWorkflow.go();
        }

    }

    private static class ThreadWriteLog extends Thread {

        @Override
        public void run() {
            WriteLogFile.writeLog(AVMWorkflow.logList, avm.AVM.logPath);
            WebDriverTool.killChromeProcess();

        }
    }

    private static class SwingWorkerUI extends SwingWorker<Void, Void> {

        public SwingWorkerUI() {
        }

        @Override
        protected Void doInBackground() throws Exception {
            new MainFrame().setVisible(true);
            return null;

        }
    }

}
