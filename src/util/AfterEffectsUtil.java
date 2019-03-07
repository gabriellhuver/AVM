/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import static avm.AVM.settings;
import static core.AVMWorkflow.log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Scanner;

/**
 *
 * @author gab00
 */
public class AfterEffectsUtil {

    public static void copyFilesToPath(String downloadFilesPath, boolean deleteTemp) {
        if (deleteTemp) {
            try {
                log("deleting temp files");
                deleteTemporaryFiles();

            } catch (Exception e) {
                log(e.getMessage());

            }

        }

        makeCopy(downloadFilesPath);

    }

    public static void deleteTemporaryFiles() {
        log("deleting temporary files");
        File deleteFilesFolder = new File(settings.getVideoRenderFolder());
        for (File object : deleteFilesFolder.listFiles()) {
            object.delete();
        }
    }

    private static void makeCopy(String downloadFilesPath) {

        File folder = new File(downloadFilesPath);
        if (folder.exists()) {
            File[] listFiles = folder.listFiles();
            try {
                log("[FILE COPY]");
                copyFileUsingStream(new File(settings.getAssetsFolder() + "//intro.mov"), new File(settings.getVideoRenderFolder() + "//1.mov"));
                log("intro copied");
            } catch (IOException ex) {
                Logger.getLogger(AfterEffectsUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < listFiles.length; i++) {
                try {
                    copyFileUsingStream(listFiles[i], new File(settings.getVideoRenderFolder() + "//" + (i + 2) + ".mp4"));
                    log("file copied " + listFiles[i].getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(AfterEffectsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                copyFileUsingStream(new File(settings.getAssetsFolder() + "//transition.mov"), new File(settings.getVideoRenderFolder() + "//t.mov"));
                log("transition copied");

            } catch (IOException ex) {
                Logger.getLogger(AfterEffectsUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                copyFileUsingStream(new File(settings.getAssetsFolder() + "//end.mov"), new File(settings.getVideoRenderFolder() + "//" + (listFiles.length + 2) + ".mov"));
                log("end copied");

            } catch (IOException ex) {
                Logger.getLogger(AfterEffectsUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            log("[FILES PREPARED TO RENDER]");

        } else {
            log("Render Aborted ! Folder downloded files does not exist! ");
        }
    }

    public static void createProjectByScript() {
        try {
            log("creating After Effects project by Script " + settings.getAfterEffectsProjectCreatorScriptFile());
            doCommandCreateProject(settings.getAfterEffectsExec(), settings.getAfterEffectsProjectCreatorScriptFile());
        } catch (IOException | InterruptedException ex) {
            log(ex.getMessage());
        }

    }

    public static void createProjectByScript(String script) {
        try {
            log("creating After Effects project by Script " + script);
            doCommandCreateProject(settings.getAfterEffectsExec(), script);
        } catch (IOException | InterruptedException ex) {
            log(ex.getMessage());
        }

    }

    public static void killAfterEffects() {
        try {
            log("killing After effects process!");
            Runtime.getRuntime().exec("taskkill /F /IM AfterFX.exe /T");

        } catch (IOException ex) {
            log(ex.getMessage());
        }

    }

    public static void renderTemplate() throws IOException, InterruptedException {
        String path = settings.getAeRenderExec();
        String proj = settings.getAfterEffectsProjectFile();
        String outPut = settings.getFinalVideoPath();
        String comp = settings.getCompositionAEName();
        String outPutTemplate = settings.getAeRenderOutputSettings();
        String RenderAESetings = settings.getAeRenderRenderSettings();
        log("loading render config");

        doCommandRender(path, proj, outPut, comp, RenderAESetings, outPutTemplate);
    }

    public static void doCommandCreateProject(String path, String cmd) throws IOException, InterruptedException {
        boolean check = false;
        check = checkFiles();
        if (checkFiles()) {
            log("Check Files OK!");

            log("**********************");
            log("Runnig After Effects Script " + cmd + " on " + path);
            log("*****  Please Wait ********");

            Runtime rt = Runtime.getRuntime();
            String[] commands = new String[]{path, "-r", cmd};
            Process proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                log(s);
            }
            log("Adobe After Effects project created based on " + path + " template if had no errors kkkkkk");

        } else {
            String userReturn = "";
            System.out.print("Error on files check, copy again? [Y/N]:");
            Scanner myScanner = new Scanner(System.in);
            userReturn = myScanner.next();
            while (!checkFiles()) {
                userReturn = "";
                System.out.print("Continue ? [Y/N]:");
                userReturn = myScanner.next();
                switch (userReturn.toUpperCase()) {
                    case "Y":
                        doCommandCreateProject(path, cmd);
                        break;
                    case "N":
                        break;
                    default:
                        break;
                }

            }
        }

    }

    private static void doCommandRender(String path, String proj, String outPut, String comp, String settings, String outputTemplate) throws IOException {
        log("#########################################");
        log("Initializing RENDER !");
        log("#########################################");
        log("Template Path ->" + proj + "");
        log("#########################################");
        log("Output File -> " + outPut);
        log("#########################################");
        log("Render Setting -> " + settings);
        log("#########################################");

        Runtime rt = Runtime.getRuntime();
        String[] commands = new String[]{path, "-project", proj, "-comp", comp, "-RStemplate", settings, "-output", outPut, "-OMtemplate", outputTemplate};
        log("Command Line: " + Arrays.toString(commands));
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {

            log(s);

        }

        log("--------------------- Render success ---------------------");

    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        log("Copy file " + source.getAbsolutePath() + " -> " + dest.getAbsolutePath());
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {

            log(e.getMessage());
        } finally {
            is.close();
            os.close();
            log("File copy success");
        }
    }

    private static boolean checkFiles() {
        boolean r = false;
        log("Checking video Render folder files");
        File folder = new File(settings.getVideoRenderFolder());
        if (folder.exists()) {
            File[] listFiles = folder.listFiles();
            try {
                if (listFiles.length == settings.getFilesToDownload() + 3) {
                    r = true;
                }
            } catch (Exception e) {
                r = true;
            }

        }
        return r;
    }

}
