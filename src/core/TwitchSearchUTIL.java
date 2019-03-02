/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import avm.AVM;
import static avm.AVM.settings;
import static core.AVMWorkflow.log;
import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import util.DownloadUtil;
import util.WebDriverTool;
import objects.YoutubeVideo;

/**
 *
 * @author gab00
 */
public class TwitchSearchUTIL {

    public WebDriver driver;

    public List<String> getListClipsByURL(String url, int count) throws FileNotFoundException {
        List<String> Alllinks = new ArrayList<>();

        while (Alllinks.isEmpty()) {
            try {
                driver = WebDriverTool.setupDriver();
                driver.get(url);
                // driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/main/div[1]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[1]/div/div/div")).click();
                // driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/main/div[1]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[1]/div/div/div/div[2]/div[1]/div/div[3]/div/div/div[2]/div/label")).click();

                WebElement videoBox = driver
                        .findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/main/div[1]/div[3]/div/div/div/div[3]"));
                List<WebElement> findElements = videoBox.findElements(By.tagName("a"));
                List<String> links = new ArrayList<>();
                int i = 1;
                for (WebElement webElement : findElements) {
                    if (webElement.getAttribute("href").contains("clip")) {
                        if (!Alllinks.contains(webElement.getAttribute("href"))) {
                            if (i <= count) {
                                Alllinks.add(webElement.getAttribute("href"));
                                i++;
                            }

                        }
                    }
                }

            } catch (Exception e) {
                AVMWorkflow.log(e.getMessage());
                driver.close();
                getListClipsByURL(url, count);
            } finally {
                driver.close();
                if (Alllinks.isEmpty()) {
                    getListClipsByURL(url, count);
                }

            }
        }

        return Alllinks;
    }

    public void downloadClip(String string) throws InterruptedException {
        String attribute = getLinkAttr(string);

        try {
            log(" download from url " + attribute);
            if (attribute.equals("")) {
                downloadClip(string);
                log("Download file url not found!");

                throw new Exception("Download file url not found!");
            }
            if (!attribute.equals("")) {
                DownloadUtil.fileDownload(attribute, AVM.tempDownloadPath);

            } else {
                log("search for mp4 link from twitch clip page failed");
                downloadClip(string);
            }
        } catch (Exception e) {
            log("Erro on download file " + attribute + " - " + AVM.tempDownloadPath);
            downloadClip(string);
        }
    }

    private String getLinkAttr(String string) throws InterruptedException {
        log("Capturing download link from " + string);
        try {
            driver = WebDriverTool.setupDriver();
            driver.get(string);
            Thread.sleep(3000);
            String attribute = driver.findElement(By.xpath(
                    "//*[@id=\"root\"]/div/div[2]/div/main/div[2]/div[3]/div/div/div[2]/div/div[2]/div/div[1]/video"))
                    .getAttribute("src");
            return attribute;
        } catch (Exception e) {
            log("Error on getLinkAttr");
            return null;
        } finally {

            driver.close();
        }
    }

    public void doUpload(YoutubeVideo video) throws InterruptedException, AWTException {

        try {

            //JSONUtil.getVideoConfig(jsonTempFile);
            log(" ng Youtube UPLOAD Robot ");
            driver = WebDriverTool.setupDriver();
            log("Getting URL " + settings.getYoutubeUploadUrl());
            driver.get(settings.getYoutubeUploadUrl());
            log("Finding Youtube UPLOAD input and send file to upload");
            Thread.sleep(1500);
            driver.findElement(By.xpath("//*[@id=\"upload-prompt-box\"]/div[2]/input")).sendKeys(video.getConvertedFile());

            log("File " + video.getFileName() + " is uploading");
            driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[2]/div/div/div[1]/div[3]/form/div[1]/fieldset[1]/div/label[1]/span/input")).clear();
            log("Setting YOUTUBE video tittle to " + video.getYoutubeVideoTittle());
            driver.findElement(By
                    .xpath(YoutubeVideoTittleXPATH))
                    .sendKeys(video.getYoutubeVideoTittle());
            log("Video tittle OK!");
            String myText = "";
            video.getDescription().stream().map((desc) -> {
                driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[2]/div/div/div[1]/div[3]/form/div[1]/fieldset[1]/div/label[2]/span/textarea"))
                        .sendKeys(desc + "\n");
                return desc;
            }).forEachOrdered((desc) -> {
                log("Description ROW Added -> " + desc);
            });
            log("Finding tag element");
            WebElement tagElement = this.driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[2]/div/div/div[1]/div[3]/form/div[1]/fieldset[1]/div/div/span/div/span/input"));
            for (String tag : video.getTags()) {
                tagElement.sendKeys(tag + "\n");
                Thread.sleep(300);
                log("TAG: " + tag + " added");

            }
            log("Publish privacy: " + video.getStatus());
            Select dropdown = new Select(this.driver.findElement(By.name("privacy")));
            switch (video.getStatus()) {
                case "PRIVATE":
                    dropdown.selectByValue("private");
                    break;
                case "PUBLIC":
                    dropdown.selectByValue("public");
                    break;
                case "UNLISTED":
                    dropdown.selectByValue("unlisted");
                    break;
                default:
                    break;
            }

            log("Monitoring UPLOAD!");
            String text = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[2]/div[2]/div[2]/span[1]/span")).getText();
            String timeLeft;
            String percentage = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[2]/div[2]/div[1]/span/span")).getText();
            while (percentage.length() > 1) {
                text = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[2]/div[2]/div[2]/span[1]/span")).getText();
                timeLeft = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[2]/div[2]/span")).getText();
                percentage = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[2]/div[2]/div[1]/span/span")).getText();
                log("Upload STATUS: " + percentage + " " + timeLeft);
                Thread.sleep(1500);
            }
            log("Youtube Upload video Completed! ");
            String uTubeUrl = driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[2]/div[2]/div[2]/a")).getText();
            log("URL: " + uTubeUrl);
            log("Video saved in " + video.getConvertedFile());
            JOptionPane.showMessageDialog(null, "VIDEO: " + video.getYoutubeVideoTittle() + " upload successful!");

            driver.findElement(By.xpath("//*[@id=\"upload-item-0\"]/div[3]/div[1]/div[1]/div/div/button")).click();
            String userReturn = "";
            log("Continue ?");
            Scanner myScanner = new Scanner(System.in);
            userReturn = myScanner.next();
            driver.close();
            AVMWorkflow.go();

        } catch (Exception e) {
            driver.close();
            log(e.getMessage());

            log("Erro on upload verify files and try again ? [Y/N]");
            Scanner myScanner = new Scanner(System.in);
            String userReturn = myScanner.next();
            switch (userReturn.toUpperCase()) {
                case "Y":

                    AVMWorkflow.makeUpload();
                    break;
                default:
                    break;

            }
        }

    }

    private static final String YoutubeVideoTittleXPATH = "//*[@id=\"upload-item-0\"]"
            + "/div[3]/div[2]/div/div/div[1]"
            + "/div[3]/form/div[1]/fieldset[1]/"
            + "div/label[1]/span/input";
}