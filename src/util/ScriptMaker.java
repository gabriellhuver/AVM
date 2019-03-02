/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import static core.AVMWorkflow.log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gab00
 */
public class ScriptMaker {

    static String scriptAddSequence = "add_sequence(\"C:/temp/TempVideoRenderizer/VideoIntroName\");";
    static String scriptIntro = "var intro = myComp.layers.add(selection[SelectionCount]);intro.startTime = 0;scaleLayers(intro);";
    static String scriptVideo = "var videoSelectionCount = myComp.layers.add(selection[Count]);videoSelectionCount.startTime = intro.outPoint;scaleLayers(videoCount);";
    static String scriptEnd = "var end = myComp.layers.add(selection[##]);end.startTime = video!!.outPoint;scaleLayers(end);";
    static String convertCommand = "HandBrakeCLI -i \"%FROM%\" -o \"%TO%\" -e nvenc_h264 -q 22 -r 60 -B 64 -O  > |LOG| ";

    public static String createTempBatEncodeFile(String from, String to) {
        try {
            log("Creating convert batch file");
            String fileName = "convert" + String.valueOf(System.currentTimeMillis()) + ".bat";
            BufferedWriter writer = null;
            log("bat file -> " + fileName);
            writer = new BufferedWriter(new FileWriter(avm.AVM.settings.getHandBrakeExec() + "//" + fileName));
            writer.write(convertCommand.replace("%FROM%", from).replace("%TO%", to).replace("|LOG|", fileName.replace(".bat", ".txt")));
            writer.newLine();
            writer.write("exit");
            writer.close();
            
            log("Batch file created!");
            return fileName;
        } catch (IOException ex) {
            Logger.getLogger(ScriptMaker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<String> readFile(File scriptFile) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(scriptFile),
                Charset.forName("ISO-8859-1")));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        log("File loaded -> " + scriptFile);
        return lines;
    }

    public static void createScript(String scriptPath, int videoCount, String saveFileFolder) {
        videoCount += 2;
        BufferedWriter writer = null;
        try {
            File logFile = new File(scriptPath);
            writer = new BufferedWriter(new FileWriter(saveFileFolder));
            List<String> readFile = readFile(new File(scriptPath));
            for (String string : readFile) {
                writer.newLine();

                if (string.contains("############## Imports")) {

                    writer.newLine();
                    writer.write("//-------------- Imports Added ------------");
                    writer.newLine();
                    for (int i = 1; i <= videoCount; i++) {
                        if (i == 1) {
                            writer.write(scriptAddSequence.replace("VideoIntroName", i + ".mov"));
                            writer.newLine();
                        } else if (i == videoCount) {
                            writer.write(scriptAddSequence.replace("VideoIntroName", i + ".mov"));
                            writer.newLine();
                        } else {
                            writer.write(scriptAddSequence.replace("VideoIntroName", i + ".mp4"));
                            writer.newLine();
                        }
                        writer.newLine();

                    }
                    writer.write("//-------------- Imports Added ------------");
                    writer.newLine();
                } else if (string.contains("############# Sequence clips")) {
                    writer.newLine();
                    for (int i = 1; i <= videoCount; i++) {
                        if (i == 1) {
                            //Intro
                            String[] replace = scriptIntro.replace("SelectionCount", (i - 1) + "").split(";");
                            for (String kkk : replace) {
                                writer.write(kkk + ";");
                                writer.newLine();
                            }
                            writer.newLine();

                        } else if (i == 2) {
                            //Segundo
                            String[] replace = scriptVideo.replace("SelectionCount", (i - 1) + "").replace("Count", (i - 1) + "").split(";");
                            for (String kkk : replace) {
                                writer.write(kkk + ";");
                                writer.newLine();
                            }
                            writer.newLine();

                        } else if (i == videoCount) {
                            // Ultimo
                            String[] replace = scriptEnd.replace("##", (i - 1) + "").replace("!!", (i - 2) + "").split(";");
                            for (String kkk : replace) {
                                writer.write(kkk + ";");
                                writer.newLine();
                            }
                            writer.newLine();

                        } else {
                            //resto
                            String[] replace = scriptVideo.replace("SelectionCount", (i - 1) + "").replace("intro", "video" + (i - 2)).replace("Count", (i - 1) + "").split(";");
                            for (String kkk : replace) {
                                writer.write(kkk + ";");
                                writer.newLine();
                            }
                            writer.newLine();

                        }
                        writer.newLine();

                    }
                }

                if (string.contains(";")
                        || string.contains("}")
                        || string.contains("{")
                        || string.contains("//")) {
                    writer.newLine();
                }
                writer.write(string);

            }

        } catch (Exception e) {
            e.printStackTrace();
            log(e.getMessage());

        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
                log(e.getMessage());

            }
        }

    }

}
