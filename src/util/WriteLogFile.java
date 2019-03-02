package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class WriteLogFile {

    public static void writeLog(List<String> arr, String logPath) {
        try {
            String fileName = logPath + "Log" + new Date().getTime() + ".txt";
            try (FileWriter writer = new FileWriter(fileName)) {
                for (String str : arr) {
                    writer.write("\n" + str);
                }
            }
        } catch (IOException e) {
            System.out.println("Error on log Write!");
            e.printStackTrace();
        }
    }
}
