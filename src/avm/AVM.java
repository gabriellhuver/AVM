/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avm;

import core.AVMWorkflow;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import objects.Settings;
import org.openqa.selenium.WebDriver;
import util.ConfigUtil;
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
    public static String tempDownloadPath;

    public static void main(String[] args) {
        try {
            addExitHook();
            AVM.jsonStg = args[1];
            AVM.logPath = args[3];

            loadConfig();
        } catch (Exception e) {
            System.out.println("Args Error, try -cfg x://tt//file.json -log x://log");
        } finally {
            AVMWorkflow.go();
        }

    }

    public static void loadConfig() {
        try {
            System.out.println(Settings.loadingStr);
            Thread.sleep(1250);
            AVMWorkflow.log("Loading configs...");
            settings = ConfigUtil.getSettings(jsonStg);
            AVMWorkflow.log(settings.toString());
            AVMWorkflow.log("JSON Settings file: " + jsonStg);
        } catch (FileNotFoundException | InterruptedException e) {
            AVMWorkflow.log("Erro on load Settings!");
        }
    }

    private static void createLog() {
        WriteLogFile.writeLog(new ArrayList<>(), logPath);

    }

    public static void addExitHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                WriteLogFile.writeLog(AVMWorkflow.logList, avm.AVM.logPath);
            }
        });
    }

    public static void openbrowser() {
        WebDriver driver = null;
        try {
            driver = WebDriverTool.setupDriver();

            driver.get("https://github.com/gabriellhuver/AVM");
            Scanner scan = new Scanner(System.in);
            System.out.println("Press key to continue...");
            scan.next();
        } catch (Exception e) {
            driver.close();
        } finally {

            driver.close();
            AVMWorkflow.go();
        }

    }

}
