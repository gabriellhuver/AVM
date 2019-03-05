/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import objects.YoutubeVideo;
import com.google.gson.Gson;
import static core.AVMWorkflow.log;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import objects.UploadList;

/**
 *
 * @author gab00
 */
public class JSONUtil {

    public static String saveListUpload(String jsonFilePath, List<UploadList> list) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        log(json);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFilePath), "UTF-8")) {
            gson.toJson(list, writer);
            log("JSON FILE SAVED -> " + jsonFilePath);
            log(list.toString());

        }

        return jsonFilePath;

    }

    public static String saveConfig(String jsonFilePath, YoutubeVideo video) throws FileNotFoundException {

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String json = gson.toJson(video);
        log(json);

        //2. Convert object to JSON string and save into a file directly
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFilePath), "UTF-8")) {

            gson.toJson(video, writer);
            log("JSON FILE SAVED -> " + jsonFilePath);
            log(video.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonFilePath;
    }

    public static YoutubeVideo getVideoConfig(String videoConfigJSONPath) throws FileNotFoundException {

        Gson gson = new Gson();

        try (Reader reader = new FileReader(videoConfigJSONPath)) {

            YoutubeVideo video = gson.fromJson(reader, YoutubeVideo.class);
            log("Loading Video Config from -> " + videoConfigJSONPath);
            return video;
        } catch (IOException e) {
            log(e.getMessage());
            return null;
        }
    }

    public static List<String> searchFilesFrom(String MAIN_FOLDER, String ext) {
        File f = new File(MAIN_FOLDER);
        List<String> fs = new ArrayList<>();
        if (ext.equals("")) {
            File[] listFiles = f.listFiles();
            for (File listFile : listFiles) {
                if (listFile.isDirectory()) {
                    fs.add(listFile.getAbsolutePath());
                }
            }
        } else {
            if (f.isDirectory()) {
                File[] listFiles = f.listFiles();
                for (File listFile : listFiles) {
                    if (listFile.getAbsolutePath().contains(ext)) {
                        fs.add(listFile.getAbsolutePath());
                    }
                }
            }
        }
        return fs;

    }

}
