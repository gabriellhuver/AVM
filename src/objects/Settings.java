/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author gab00
 */
public class Settings {

    public static final String loadingStr ="\n \n \n \n \n \n \n \n \n \n \n"+ 
              "                                                                                                        \n"
            + "                                                                                                        \n"
            + "                                                                                                        \n"
            + "                                                                                                        \n"
            + "                                                                                                        \n"
            + "                                      %%%%%%%%%%%%%%%%%%%%%%%%%                                         \n"
            + "                                     %%%%%%%%%%%%%%%%%%%%%%%%%%                                         \n"
            + "                                    %%%%                     %%                                         \n"
            + "                                    %%%%                     %%                                         \n"
            + "                                    %%%%       %%%    %%     %%                                         \n"
            + "                                    %%%%       %%%    %%     %%                                         \n"
            + "                                    %%%%       %%%    %%     %%                                         \n"
            + "                                    %%%%       %%%    %%     %%                                         \n"
            + "                                    %%%%                    %%%                                         \n"
            + "                                    %%%%                  %%%%                                          \n"
            + "                                    %%%%%%%%%%   #%%%%%%%%%%                                            \n"
            + "                                    %%%%%%%%%% %%%%%%%%%%%                                              \n"
            + "                                    (((((((%%%%%%%((((((                                                \n"
            + "                                           %%%%%                                                        \n"
            + "                                                                                                        \n"
            + "                        @@@   @@@   @@@  @@@ @@@@ @@ @@@  @@@ @@@@  @@@  @@@                            \n"
            + "                        @     @@@    @@  @@   @@@     @@  @@   @@@  @@@   @@                            \n"
            + "                        @    @ @@@   @@  @@   @@@ @   @@  @@    @@@ @@@   @@                            \n"
            + "                        @    @ @@@   @@  @@   *@@&    @@  @@   @@@@ @@@   @@                            \n"
            + "                        @   .  @@@.  @@  @@    @@@    @@  @@   @ @@ @@@   @@                            \n"
            + "                        @  @@@ @@@@  @@  @@    %@%    @@  @@  @@@@ @@@@.  @@                            \n"
            + "                        @@@         @@@  @@@         @@@  @@@            @@@                            \n"
            + "                                                                                                        \n"
            + "                                                                                                        \n";

    private String chromeDriver;
    private String chromeUserData;
    private String mainVideoPath;

    private String CompositionAEName;

    private String videoRenderFolder;
    private String AssetsFolder;

    private String afterEffectsProjectCreatorScriptFile;
    private String afterEffectsExec;
    private String afterEffectsProjectFile;
    private String finalVideoPath;
    private String finalVideoConvertedPath;

    private String aeRenderExec;

    private String aeRenderCompositionName;
    private String aeRenderOutputSettings;
    private String aeRenderRenderSettings;

    private String handBrakeExec;

    private String youtubeUploadUrl;

    private String videoJsonFile;

    private int filesToDownload;

    public int getFilesToDownload() {
        return filesToDownload;
    }

    public void setFilesToDownload(int filesToDownload) {
        this.filesToDownload = filesToDownload;
    }

    public String getVideoJsonFile() {
        return videoJsonFile;
    }

    public void setVideoJsonFile(String videoJsonFile) {
        this.videoJsonFile = videoJsonFile;
    }

    public String getYoutubeUploadUrl() {
        return youtubeUploadUrl;
    }

    public void setYoutubeUploadUrl(String youtubeUploadUrl) {
        this.youtubeUploadUrl = youtubeUploadUrl;
    }

    public String getFinalVideoConvertedPath() {
        return finalVideoConvertedPath;
    }

    public void setFinalVideoConvertedPath(String finalVideoConvertedPath) {
        this.finalVideoConvertedPath = finalVideoConvertedPath;
    }

    public String getHandBrakeExec() {
        return handBrakeExec;
    }

    public void setHandBrakeExec(String handBrakeExec) {
        this.handBrakeExec = handBrakeExec;
    }

    public String getAeRenderCompositionName() {
        return aeRenderCompositionName;
    }

    public void setAeRenderCompositionName(String aeRenderCompositionName) {
        this.aeRenderCompositionName = aeRenderCompositionName;
    }

    public String getAeRenderOutputSettings() {
        return aeRenderOutputSettings;
    }

    public void setAeRenderOutputSettings(String aeRenderOutputSettings) {
        this.aeRenderOutputSettings = aeRenderOutputSettings;
    }

    public String getAeRenderRenderSettings() {
        return aeRenderRenderSettings;
    }

    public void setAeRenderRenderSettings(String aeRenderRenderSettings) {
        this.aeRenderRenderSettings = aeRenderRenderSettings;
    }

    public String getFinalVideoPath() {
        return finalVideoPath;
    }

    public void setFinalVideoPath(String finalVideoPath) {
        this.finalVideoPath = finalVideoPath;
    }

    public String getAfterEffectsProjectFile() {
        return afterEffectsProjectFile;
    }

    public void setAfterEffectsProjectFile(String afterEffectsProjectFile) {
        this.afterEffectsProjectFile = afterEffectsProjectFile;
    }

    public String getAeRenderExec() {
        return aeRenderExec;
    }

    public void setAeRenderExec(String aeRenderExec) {
        this.aeRenderExec = aeRenderExec;
    }

    public String getAfterEffectsExec() {
        return afterEffectsExec;
    }

    public void setAfterEffectsExec(String afterEffectsExec) {
        this.afterEffectsExec = afterEffectsExec;
    }

    public String getAfterEffectsProjectCreatorScriptFile() {
        return afterEffectsProjectCreatorScriptFile;
    }

    public void setAfterEffectsProjectCreatorScriptFile(String afterEffectsProjectCreatorScriptFile) {
        this.afterEffectsProjectCreatorScriptFile = afterEffectsProjectCreatorScriptFile;
    }

    public String getAssetsFolder() {
        return AssetsFolder;
    }

    public void setAssetsFolder(String AssetsFolder) {
        this.AssetsFolder = AssetsFolder;
    }

    public String getCompositionAEName() {
        return CompositionAEName;
    }

    public void setCompositionAEName(String CompositionAEName) {
        this.CompositionAEName = CompositionAEName;
    }

    public String getVideoRenderFolder() {
        return videoRenderFolder;
    }

    public void setVideoRenderFolder(String videoRenderFolder) {
        this.videoRenderFolder = videoRenderFolder;
    }

    public String getChromeDriver() {
        return chromeDriver;
    }

    public void setChromeDriver(String chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public String getChromeUserData() {
        return chromeUserData;
    }

    public void setChromeUserData(String chromeUserData) {
        this.chromeUserData = chromeUserData;
    }

    public String getMainVideoPath() {
        return mainVideoPath;
    }

    public void setMainVideoPath(String mainVideoPath) {
        this.mainVideoPath = mainVideoPath;
    }

    @Override
    public String toString() {
        return "Settings{" + "chromeDriver=" + chromeDriver + ", chromeUserData=" + chromeUserData + ", mainVideoPath=" + mainVideoPath + ", CompositionAEName=" + CompositionAEName + ", videoRenderFolder=" + videoRenderFolder + ", AssetsFolder=" + AssetsFolder + ", afterEffectsProjectCreatorScriptFile=" + afterEffectsProjectCreatorScriptFile + ", afterEffectsExec=" + afterEffectsExec + ", afterEffectsProjectFile=" + afterEffectsProjectFile + ", finalVideoPath=" + finalVideoPath + ", finalVideoConvertedPath=" + finalVideoConvertedPath + ", aeRenderExec=" + aeRenderExec + ", aeRenderCompositionName=" + aeRenderCompositionName + ", aeRenderOutputSettings=" + aeRenderOutputSettings + ", aeRenderRenderSettings=" + aeRenderRenderSettings + ", handBrakeExec=" + handBrakeExec + ", youtubeUploadUrl=" + youtubeUploadUrl + ", videoJsonFile=" + videoJsonFile + ", filesToDownload=" + filesToDownload + '}';
    }

}
