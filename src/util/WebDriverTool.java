package util;

import avm.AVM;
import static core.AVMWorkflow.log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverTool {

    public static void killChromeProcess() {
        try {
            log("Killing chrome process!");
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T");

        } catch (IOException ex) {
            log(ex.getMessage());
        }

    }

    public static WebDriver setupDriver() {
        try {
            WebDriver driver;

            System.setProperty("webdriver.chrome.driver", AVM.settings.getChromeDriver());
            log("loading chrome driver");
            log("Chrome driver path: " + AVM.settings.getChromeDriver());
            ChromeOptions options = new ChromeOptions();
            // options.addArguments("--test-type");
            options.addArguments("--use-fake-ui-for-media-stream");
            options.addArguments("--use-fake-device-for-media-stream");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            // options.addExtensions(new File(pluginPath));
            // DesiredCapabilities capabilities = new DesiredCapabilities();
            // capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            options.addArguments("--mute-audio");
            options.addArguments("user-data-dir=" + AVM.settings.getChromeUserData());
            log("UserData: " + AVM.settings.getChromeUserData());
            //AVMWorkflow.log("Options");
            
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            log("chrome driver ready");
            return driver;
        } catch (Exception e) {
            log(e.getMessage());
            return null;
        }
    }

}
