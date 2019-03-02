package util;

import core.AVMWorkflow;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Random;

public class DownloadUtil {

    final static int size = 1024;

    public static void fileDownload(String fAddress, String destinationDir) {
        int slashIndex = fAddress.lastIndexOf('/');
        int periodIndex = fAddress.lastIndexOf('.');

        String fileName = fAddress.substring(slashIndex + 1);

        if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1) {
            AVMWorkflow.log("---------- File Download Started WAIT -------");
            download(fAddress, destinationDir, fileName);
        } else {
            AVMWorkflow.log("path or file name error : " + fAddress + " - " + destinationDir);
        }
    }

    public static void download(String remotePath, String localPath, String filename) {
        AVMWorkflow.log("---------- File Download Started WAIT -------");
        AVMWorkflow.log("Downloading file " + remotePath + " - > " + localPath);
        BufferedInputStream in = null;
        FileOutputStream out = null;
        DecimalFormat df = new DecimalFormat("#.00");

        try {
            URL url = new URL(remotePath);
            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();

            if (size < 0) {
                AVMWorkflow.log("Could not get the file size");
            } else {
                double x = size / 1024;
                String format = df.format((x / 1024));
                AVMWorkflow.log("File size: " + format + " MB");
            }

            in = new BufferedInputStream(url.openStream());
            out = new FileOutputStream(localPath + "//" + filename);
            byte data[] = new byte[1024];
            int count;
            double sumCount = 0.0;

            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);

                sumCount += count;
                if (size > 0) {
                    double prctg = (sumCount / size * 100.0);
                    String format = df.format(prctg);

                    if (new Double(format) >= 1) {
                        System.out.print("\rFile Download: | " + format + "% | " + remotePath + " -> " + localPath);
                    }
                }
            }

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

}
