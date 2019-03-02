/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avm;

import core.AVMWorkflow;
import java.util.ArrayList;
import objects.Settings;
import util.ConfigUtil;
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

            AVMWorkflow.log("Loading configs...");
            settings = ConfigUtil.getSettings(jsonStg);
            AVMWorkflow.log(settings.toString());
            AVMWorkflow.log("JSON Settings file: " + jsonStg);
        } catch (Exception e) {
            AVMWorkflow.log("Erro on load Settings!");
        }
    }

    private static void createLog() {
        WriteLogFile.writeLog(new ArrayList<String>(), logPath);

    }

    public static void addExitHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                WriteLogFile.writeLog(AVMWorkflow.logList, avm.AVM.logPath);
            }
        });
    }

}
