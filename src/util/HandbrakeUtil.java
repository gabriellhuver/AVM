/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import static core.AVMWorkflow.log;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.AfterEffectsUtil;

/**
 *
 * @author gab00
 */
public class HandbrakeUtil {

    public static void killHandBrakeProcess() {
        try {
            log("Killing Handbrake process!");
            Runtime.getRuntime().exec("taskkill /F /IM HandBrakeCLI.exe /T");

        } catch (IOException ex) {
            log(ex.getMessage());
        }

    }

    public static void convertFile(String runBat) throws InterruptedException {
        killHandBrakeProcess();
        new Thread(() -> {
            try {
                log("Running HandBrake Script - > " + runBat);
                File dir = new File(avm.AVM.settings.getHandBrakeExec());
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start", runBat);
                pb.directory(dir);
                Process p = pb.start();
                p.waitFor();
            } catch (IOException ex) {
                Logger.getLogger(HandbrakeUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(HandbrakeUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        while (true) {
            try {
                File file = new File(avm.AVM.settings.getHandBrakeExec() + "//" + runBat.replace(".bat", ".txt"));
                boolean fileIsNotLocked = file.renameTo(file);
                if (fileIsNotLocked) {
                    System.out.println("Convert finalized!");
                    Thread.sleep(5000);
                    try {
                        File f = new File(avm.AVM.settings.getFinalVideoPath().replace("finalVideo", "convertedFinalVideo"));
                        AfterEffectsUtil.copyFileUsingStream(f, new File("C:\\temp\\Backup\\convertedFileBackup" + new Date().getTime() + ".mov"));
                        avm.AVM.settings.setFinalVideoConvertedPath(f.getAbsolutePath());
                    } catch (Exception e) {
                        convertFile(runBat);
                    }
                    break;
                }

            } catch (Exception e) {
                log("File not ready yet");
            }
        }
    }

}
