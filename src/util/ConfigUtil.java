/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import core.AVMWorkflow;
import static core.AVMWorkflow.log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import objects.Settings;

/**
 *
 * @author gab00
 */
public class ConfigUtil {

    public static String saveSettings(String jsonFilePath, Settings setg) throws FileNotFoundException {
        log("Saving Settings on " + jsonFilePath);
        Gson gson = new Gson();
        String json = gson.toJson(setg);
        log(json);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFilePath), "UTF-8")) {
            gson.toJson(setg, writer);
            AVMWorkflow.log("JSON FILE SAVED -> " + jsonFilePath);
            AVMWorkflow.log(setg.toString());
        } catch (IOException e) {
            log(e.getMessage());
        }

        return jsonFilePath;
    }

    public static Settings getSettings(String videoConfigJSONPath) throws FileNotFoundException {

        Gson gson = new Gson();

        try (Reader reader = new FileReader(videoConfigJSONPath)) {

            Settings setg = gson.fromJson(reader, Settings.class);
            log("Loading Settings from -> " + videoConfigJSONPath);
            return setg;
        } catch (IOException e) {
            log("Erro on get settings file");
            return null;
        }
    }
}
