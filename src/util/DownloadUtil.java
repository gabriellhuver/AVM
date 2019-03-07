package util;

import static core.AVMWorkflow.log;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Date;

public class DownloadUtil {

    final static int size = 1024;

    public static void fileDownload(String fAddress, String destinationDir) {
        int slashIndex = fAddress.lastIndexOf('/');
        int periodIndex = fAddress.lastIndexOf('.');

        String fileName = fAddress.substring(slashIndex + 1);

        if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1) {
            download(fAddress, destinationDir, fileName);
            log(fileName + " - > " + destinationDir);
        } else {
            log("path or file name error : " + fAddress + " - " + destinationDir);
        }
    }

    public static void download(String remotePath, String localPath, String filename) {
        long currentTimeMillis = System.currentTimeMillis();
        BufferedInputStream in = null;
        FileOutputStream out = null;
        DecimalFormat df = new DecimalFormat("#.00");

        try {
            URL url = new URL(remotePath);
            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();
            log("---------------");
            log("[Download info]");
            log("File name: " + filename);
            log("Local Path: " + localPath);
            log("Remote path: " + remotePath);
            log("---------------");

            if (size < 0) {
                log("Could not get the file size");
            } else {
                double x = size / 1024;
                String format = df.format((x / 1024));
                log("File size: " + format + " MB");
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
                        System.out.print("\r" + new Date().toString() + ": File Download: | " + format + "% | " + filename + " ...");
                    }
                }
            }

        } catch (MalformedURLException e1) {
            log(e1.getMessage());
        } catch (IOException e2) {
            log(e2.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e3) {
                    log(e3.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e4) {
                    log(e4.getMessage());
                }
            }
            log("---");
            log("Download success in " + (System.currentTimeMillis() - currentTimeMillis) / 1024 + " Seconds");
        }
    }

}
