package eBay;

import eBay.drivers.DriverManager;
import eBay.listeners.TestReporter;
import eBay.listeners.TestResultsListener;
import eBay.pageobjects.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.logging.LogManager;
import java.util.logging.Logger;

@Listeners({TestResultsListener.class, TestReporter.class})
public abstract class BaseTest {
    protected static final double PRICE_DELTA = 0.0001;

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    protected final Logger log = LogManager.getLogManager().getLogger(this.getClass().getSimpleName());
    protected SoftAssert softAssert;

    protected MainLayoutPage mainLayoutPage;
    protected HomePage homePage;
    protected SearchResultsPage resultsPage;
    protected ProductPage productPage;
    protected AddedToCartPopUp addedToCartPopUp;
    protected CartPage cartPage;

    @Parameters({"browserName", "mode"})
    @BeforeClass
    public void setup(@Optional("Chrome") String browserName, @Optional("Local") String mode) {
        driver.set(DriverManager.getDriver(browserName, mode));
        ITestContext context = Reporter.getCurrentTestResult().getTestContext();
        context.setAttribute("WebDriver", driver.get());
        softAssert = new SoftAssert();

        mainLayoutPage = new MainLayoutPage(driver.get());
        homePage = new HomePage(driver.get());
        resultsPage = new SearchResultsPage(driver.get());
        productPage = new ProductPage(driver.get());
        addedToCartPopUp = new AddedToCartPopUp(driver.get());
        cartPage = new CartPage(driver.get());
    }

    @AfterMethod
    public void browserReset() {
        driver.get().manage().deleteAllCookies();
    }

    @AfterClass
    public void cleanUp() {
        driver.get()
                .manage()
                .deleteAllCookies();
        driver.get()
                .quit();
        driver.remove();
    }
}
