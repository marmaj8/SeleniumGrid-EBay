package eBay.drivers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class.getSimpleName());

    public static WebDriver getDriver(String driverType) {
        return getDriver(driverType, "LOCAL");
    }

    public static WebDriver getDriver(String driverType, String gridMode) {

        switch (driverType.toUpperCase(Locale.ROOT)) {
            case "CHROME":
                log.info("Chrome driver selected");
                return gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getChromeOptions()) : getLocalChrome();

            case "FIREFOX":
                log.info("Firefox driver selected");
                return gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getFirefoxOptions()) : getLocalFirefox();

            case "EDGE":
                log.info("Edge driver selected");
                return gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getEdgeOptions()) : getLocalEdge();

            default:
                log.info("Driver '" + driverType.toUpperCase(Locale.ROOT) + "' not recognized");
                return null;
        }
    }

    private static WebDriver getLocalChrome() {
        log.info("Setting up new ChromeDriver");
        System.setProperty("webdriver.chrome.driver", System.getProperty("driver.path") + "/chromedriver.exe");
        return new ChromeDriver(CapabilityManager.getChromeOptions());
    }

    private static WebDriver getLocalFirefox() {
        //firefox requires not only browser to be installed but also profile to be created.
        log.info("Setting up new FirefoxDriver");
        System.setProperty("webdriver.gecko.driver", System.getProperty("driver.path") + "/geckodriver.exe");
        return new FirefoxDriver(CapabilityManager.getFirefoxOptions());
    }

    private static WebDriver getLocalEdge() {
        log.info("Setting up new EdgeDriver");
        System.setProperty("webdriver.edge.driver", System.getProperty("driver.path") + "/msedgedriver.exe");
        return new EdgeDriver(CapabilityManager.getEdgeOptions());
    }

    private static WebDriver getRemoteDriver(Capabilities capabilities) {
        WebDriver requestedDriver = null;
        try {
            requestedDriver = new RemoteWebDriver(new URL(System.getProperty("selenium.grid.url")), capabilities);
        } catch (MalformedURLException e) {
            log.error("Remote driver creation failed");
        }
        return requestedDriver;
    }
}
